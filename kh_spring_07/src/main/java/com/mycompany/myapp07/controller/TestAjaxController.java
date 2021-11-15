package com.mycompany.myapp07.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mycompany.myapp07.model.domain.Sample;
import com.mycompany.myapp07.model.domain.User;

@Controller
public class TestAjaxController {
	private Logger logger = LoggerFactory.getLogger(TestAjaxController.class);

	@RequestMapping(value = "testajax", method = RequestMethod.GET)
	public ModelAndView testajax(ModelAndView mv) {
		mv.setViewName("testajax");
		return mv;
	}

	@RequestMapping(value = "testajax", method = RequestMethod.POST)
	@ResponseBody
	public String testajaxData() {
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		map.put("k1", 1);
		JSONObject job = new JSONObject();
		job.put("k1", 1);
		String result = job.toJSONString();
		return result;
	}

	@RequestMapping(value = "test1.do", method = RequestMethod.POST)
	public void est1Method(@RequestParam("name") String name, @RequestParam("age") int age, Sample vo,
			HttpServletResponse response, HttpSession session) throws IOException {
		String result = "";
		if (vo.getName().equals("신사임당")) {
			session.setAttribute("samp", vo); // 전달되지 않음. 화면 reload 되어야 인지되므로 ajax로는 전달되지 않음
			result = "ok";
		} else {
			result = "fail";
		}
		response.getWriter().append(result);
		response.getWriter().flush();
		response.getWriter().close();
		// return result;
	}

	@RequestMapping(value = "test2.do", method = RequestMethod.POST)
	@ResponseBody // 결과를 response 객체에 담아서 보내는 어노테이션
	public String test2Method(HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=utf-8");

		JSONObject job = new JSONObject();
		// Map 형식의 JSONObject 객체를 생성하여 출력할 값을 Key와 Value 형태로 담는다
		job.put("no", 123);
		job.put("title", "test return json object");
		// 한글 전송 시 깨질 우려가 있으므로, URLEncoder로 UTF-8방식의 인코딩을 처리한다
		job.put("writer", "홍길동");
		job.put("content", "json 객체를 뷰로 리턴하는 테스트");

		// JSONObject 를 string 형태로 리턴한다.
		return job.toJSONString();
	}

	@RequestMapping(value = "test3.do", method = RequestMethod.POST)
	public void test3Method(HttpServletResponse response) throws IOException {
		logger.info("test3Method() run...");
		// error // warn // info // debug // trace
		// 레벨
		// FATAL - ERROR - WARN(오류 우려) - INFO(상태변화정보메시지) - DEBUG - TRACE
		logger.error("에러메시지");
		logger.warn("warn메시지");
		logger.debug("debug 메시지");

		// List를 json 배열로 만들어서, 뷰로 리턴 처리한다.
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("u1234", "p1234", "홍길동", 25, "h1234@kh.org"));
		list.add(new User("u2345", "p2345", "박문수", 33, "p2345@kh.org"));
		list.add(new User("u3456", "p3456", "장영실", 45, "j3456@kh.org"));

		// 전송용 최종 json 객체
		JSONObject sendJson = new JSONObject();
		// JSONArray 객체를 생성하여 JSONObject 객체를 하나씩 담는다
		JSONArray jarr = new JSONArray();

		// list 를 jarr 에 저장 처리
		for (User user : list) {
			// user 정보 저장할 json 객체 선언
			JSONObject juser = new JSONObject();
			juser.put("userId", user.getUserId());
			juser.put("userPwd", user.getUserPwd());
			juser.put("userName", URLEncoder.encode(user.getUserName(), "utf-8"));
			juser.put("age", user.getAge());
			juser.put("email", user.getEmail());
			jarr.add(juser);
		}
		// 전송할 객체 배열을 JSONObject에 담아 한 번에 전달한다
		sendJson.put("list", jarr);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping(value = "test4.do", method = RequestMethod.POST)
	public ModelAndView test4Method(ModelAndView mv) throws UnsupportedEncodingException {
		logger.info("test4Method() run...");
		// map 객체를 ModelAndView 에 담아서 리턴한다
		Sample samp = new Sample();
		System.out.println("samp : " + samp);
//		samp.setName(URLEncoder.encode(samp.getName(), "utf-8"));
		samp.setName(URLEncoder.encode("이제이", "utf-8"));

		Map<String, Sample> map = new HashMap<String, Sample>();
		map.put("samp", samp);

		mv.addAllObjects(map);
		// 뷰지정 : jsonView를 빈으로 등록하고, goekd 객체의 id를 뷰이름으로 지정해야 함
		mv.setViewName("jsonView");

		return mv; // ajax 는 json 객체로 받음
	}

	@RequestMapping(value = "test5.do", method = RequestMethod.POST)
	public ResponseEntity<String> test5Method(HttpServletRequest request
			, @RequestBody String param) throws Exception {
		logger.info("test5Method() run...");
		request.setCharacterEncoding("utf-8");

		// 전송온 문자열을 json 객체로 변환 처리
		JSONParser parser = new JSONParser();
		JSONObject job = (JSONObject) parser.parse(param);

		String name = (String) job.get("name");
		int age = ((Long) job.get("age")).intValue();

		System.out.println("name : " + name + ", age : " + age);

		// 정상 완료됨을 클라이언트로 성공값을 보내야 함
		return new ResponseEntity<String>("successResult", HttpStatus.OK);
	}

	@RequestMapping(value = "test6.do", method = RequestMethod.POST)
	@ResponseBody
	public String test6Method(HttpServletRequest request
			, @RequestBody String param) throws Exception {
		logger.info("test6Method() run...");
		request.setCharacterEncoding("utf-8");

		System.out.println("param : " + param);
		JSONArray jarr = (JSONArray) new JSONParser().parse(param);

		System.out.println("jarr : " + jarr.size());

		for (int i = 0; i < jarr.size(); i++) {
			JSONObject job = (JSONObject) jarr.get(i);
			String name = (String) job.get("name");
			int age = ((Long) job.get("age")).intValue();

			System.out.println("name : " + name + ", age : " + age);
		}

		// 정상적으로 처리가 되었다면, 클라이언트로 성공값을 보내야 한다
		return "successResult";
	}
	
	@RequestMapping(value = "test7.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> test7Method(@RequestBody String param) throws Exception {
		logger.info("test6Method() run...");
		System.out.println("param: " + param);
//		param: [{"name":"이 이","age":30},{"name":"신사임당","age":47},{"name":"황진이","age":25}]
		
		Gson gson = new Gson();
		
		// 방법 1 항상 OK
		Sample[] reqVoArray = gson.fromJson(param, Sample[].class);
		List<Sample> reqVoList = Arrays.asList(reqVoArray);
		System.out.println(reqVoList);
//				[Sample [name=이 이, age=30], Sample [name=신사임당, age=47], Sample [name=황진이, age=25]]
		// 방법 2 안될수도 있네요~
//		List<Sample> reqVoList2 = gson.fromJson(param, new TypeToken<List<Sample>>(){}.getType());

		
		// List
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("u1234", "p1234", "홍길동", 25, "h1234@kh.org"));
		list.add(new User("u2345", "p2345", "박문수", 33, "p2345@kh.org"));
		list.add(new User("u3456", "p3456", "장영실", 45, "j3456@kh.org"));
		// List+각종data를 채운 Map 생성
		Map<String, Object> map1= new HashMap<String, Object>();
		map1.put("volist", list);
		map1.put("startNum", 1);
		map1.put("endNum", 15);
		
		return map1;
	}

}
