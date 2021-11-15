<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
	<button id="test1" type="button">테스트1</button>
	<br>
	<button type="button" id="test2">테스트2</button>
	<div id="d2"></div>
	<br>
	<button type="button" id="test3">테스트3</button>
	<br>
	<button type="button" id="test4">테스트4</button>
	<div id="d4"></div>
	<br>
	<button type="button" id="test5">테스트5</button>
	<div id="d5"></div>
	<br>
	<button type="button" id="test6">테스트6</button>
	<div id="d6"></div>
	<br>
	<button type="button" id="testMyPick">테스트MyPick</button>
	<div id="d6"></div>
<div id="responseVal">[[ ${samp} ]]</div>


<script>

$("#testMyPick").on("click", function(){
    // 자바스크립트에서 jsonArray 객체를 만들어서, 서버 컨트롤러로 보내기
    var jArray = [{"name" : "이 이", "age" : 30 }, 
      {"name" : "신사임당", "age" : 47}, 
      {"name" : "황진이", "age" : 25}]; 
    $.ajax({
      url : "test7.do",
      data : JSON.stringify(jArray),
      type : "post",
      contentType : "application/json; charset=utf-8",
      dataType: "json",
      success : function(result){
    	  console.log("7: "+ result);
    	  console.log(result);
    	  
    	  
        alert("전송성공!");
        var values = $("#d6").html();
        for(var i in jArray){
          values += "이름 : " + jArray[i].name + ", 나이 : " + jArray[i].age + "<br>";
        }
        $("#d6").html(values);
      },
      error : function(request, status, errorData){
        alert("error code : " + request.status + "\n"
            + "message : " + request.responseText + "\n"
            + "error : " + errorData);
      }
  });
});





$(function(){
	$.ajax({
		url : "testajax",
		type:"post",
		dataType:"json",
		success: function(data){
			console.log(data);
			
			//click
		},
		error : function(request, status, errorData) {
			alert("error code : "
					+ request.status + "\n"
					+ "message : "
					+ request.responseText
					+ "\n" + "error : "
					+ errorData);
		}
	});
});
$("#test6").on("click", function(){
    // 자바스크립트에서 jsonArray 객체를 만들어서, 서버 컨트롤러로 보내기
    var jArray = [{"name" : "이 이", "age" : 30 }, 
      {"name" : "신사임당", "age" : 47}, 
      {"name" : "황진이", "age" : 25}]; 
    $.ajax({
      url : "test6.do",
      data : JSON.stringify(jArray),
      type : "post",
      contentType : "application/json; charset=utf-8",
      success : function(result){
    	  console.log(result);
        alert("전송성공!");
        var values = $("#d6").html();
        for(var i in jArray){
          values += "이름 : " + jArray[i].name + ", 나이 : " + jArray[i].age + "<br>";
        }
        $("#d6").html(values);
      },
      error : function(request, status, errorData){
        alert("error code : " + request.status + "\n"
            + "message : " + request.responseText + "\n"
            + "error : " + errorData);
      }
  });
});

$("#test5").on("click", function(){
    // 자바스크립트에서 json 객체를 생성해 서버 컨트롤러로 전송한다
    var job = new Object();
    job.name = "강감찬";
    job.age = 33;
    $.ajax({
      url : "test5.do",
      data : JSON.stringify(job),
      type : "post",
      contentType : "application/json; charset=utf-8",
      success : function(result){
    	  console.log("aaa: "+result);
        alert("전송 성공!");
        $("#d5").html("전송한 json 객체의 값 : " + job.name + ", " + job.age);
      },
      error : function(request, status, errorData){
        alert("error code : " + request.status + "\n"
            + "message : " + request.responseText + "\n"
            + "error : " + errorData);
      }
  });
});

$("#test4").on("click", function(){
    // 컨트롤러에서 맵 객체를 jsonView를 사용해 json 객체로 리턴받아서 출력 처리
     $.ajax({
       url : "test4.do",
       type : "post",
       dataType : "json",
       success : function(data){
    	 console.log(data);
         $("#d4").html("받은 맵 안의 samp 객체 정보 확인<br>"
           + "이름 : " +  decodeURIComponent(data.samp.name)
           + ", 나이 : " + data.samp.age);
       },
       error : function(request, status, errorData){
         alert("error code : " + request.status + "\n"
             + "message : " + request.responseText + "\n"
             + "error : " + errorData);
       }
    });
});

$("#test3").on(
		"click",
		function() {
			// 컨트롤러로 부터 리스트를 받아서 출력한다
			$.ajax({
				url : "test3.do",
				type : "post",
				dataType : "json",
				success : function(data) {
					// 전달받은 data를 JSON 문자열 형태로 바꾼다
					var jsonStr = JSON.stringify(data);
					// 바꾼 문자열을 json 객체로 변환한다
					var json = JSON.parse(jsonStr);

					console.log(data);
					console.log(jsonStr);
					console.log(json);
					
					var values = $("#d3").html();

					for ( var i in json.list) {
						values += json.list[i].userId
								+ ", "
								+ json.list[i].userPwd
								+ ", "
								+ decodeURIComponent(json.list[i].userName)
								+ ", "
								+ json.list[i].age
								+ ", "
								+ json.list[i].email
								+ "<br>";
					}

					// values에 담은 값을 d3이라는 id의 div에 출력한다.
					$("#d3").html(values);
				},
				error : function(request, status, errorData) {
					alert("error code : "
							+ request.status + "\n"
							+ "message : "
							+ request.responseText
							+ "\n" + "error : "
							+ errorData);
				}
			});
		}
);

$("#test2").on(
		"click",
		function() {
			// test2.do 로부터 json 객체를 리턴받아, div 에 출력한다
			$.ajax({
				url : "test2.do",
				type : "post",
				dataType : "json",
				success : function(data) {
					console.log("data:"+data);
					console.log(data);
					// 전달받은 JSONObject에 담은 Value를 Key로 접근하여 출력한다
					$("#d2").html(
							"번호 : "
									+ data.no
									+ "<br>제목 : "
									+ data.title
									+ "<br>작성자 : "
									+ data.writer
									+ "<br>내용 : "
									+ decodeURIComponent(data.content
											.replace(/\+/g, " ")));
				},
				error : function(request, status, errorData) {
					alert("error code : " + request.status + "\n"
							+ "message : " + request.responseText
							+ "\n" + "error : " + errorData);
				}
			});
		}
	);
		$("#test1").on(
				"click",
				function() {
					$.ajax({
						url : "test1.do",
						data : {
							name : "신사임당",
							age : 47
						},
						type : "post",
						success : function(result) {
							console.log("result: "+result);
							console.log("samp: "+ "${samp}");
							if (result == "ok") {
								alert("전송 성공!");
							} else
								alert("전송 실패!");
						},
						error : function(request, status, errorData) {
							alert("error code : " + request.status + "\n"
									+ "message : " + request.responseText
									+ "\n" + "error : " + errorData);
						}
					});
				}
		);
</script>


[[${k2}]]<br>
[[${k2 * 1}]]<br>
[[${k2 + 2}]]<br>
[[${k2 && true}]]<br>
[[${k2 or true}]]<br>
</body>
</html>