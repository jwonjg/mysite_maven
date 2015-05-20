package com.sds.icto.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sds.icto.mysite.domain.MemberVo;
import com.sds.icto.mysite.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String joinform(){
		return "member/joinform";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute MemberVo vo){
		memberService.joinUser(vo);
		return "redirect:/member/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess(Model model){
		model.addAttribute("result", "join");
		return "member/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginform(Model model){
		model.addAttribute("result", "join");
		return "member/loginform";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(ModelAndView mav, String email, String password, HttpSession session){
		MemberVo authMember = memberService.loginUser(email, password);
		if(authMember == null) {
			mav.setViewName("redirect:/member/login?result=fail");
		}else{
			session.setAttribute("authMember", authMember);
			mav.setViewName("redirect:/index");
		}
		return mav;
	}

	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.removeAttribute("authMember");
		session.invalidate();
		return "redirect:/index";
	}
	
	@RequestMapping(value="memberinfo", method=RequestMethod.GET)
	public String memberinfoForm(){
		return "member/joinform";
	}
	
	@RequestMapping(value="memberinfo", method=RequestMethod.POST)
	public String memberinfo(MemberVo vo, HttpSession session){
		boolean isUpdated = memberService.updateMember(vo);
		if(isUpdated) {
			session.setAttribute("authMember", vo);
		}
		return "redirect:/member/memberinfosuccess";
	}
	
	@RequestMapping("memberinfosuccess")
	public ModelAndView memberinfoSuccess(ModelAndView mav){
		mav.addObject("result", "memberinfo");
		mav.setViewName("member/joinsuccess");
		return mav;
	}
	
}
