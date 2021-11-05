package com.mycompany.myapp06.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp06.member.model.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

		
	
	@RequestMapping(value = "memberlist", method = RequestMethod.GET)
	//public String
	public ModelAndView memberlist(ModelAndView mv) {
		//mv.setViewName("a/b");
		mv.setViewName("memberlist");
		mv.addObject("mlist", memberService.selectMembers());
		return mv;
	}
	@RequestMapping(value = "login", method = RequestMethod.GET)
	//public String
	public ModelAndView login(ModelAndView mv) {
		//mv.setViewName("a/b");
		mv.setViewName("memberlist");
		mv.addObject("mlist", memberService.selectMembers());
		return mv;
	}
}
