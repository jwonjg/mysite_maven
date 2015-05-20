package com.sds.icto.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sds.icto.mysite.domain.GuestbookVo;
import com.sds.icto.mysite.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping({"", "/list"})
	public String list(Model model){
		List<GuestbookVo> list = guestbookService.getAllList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(GuestbookVo vo){
		guestbookService.addGuestbook(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteForm(){
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(int no, String password){
		guestbookService.removeGuestbook(no, password);
		return "redirect:/guestbook/list";
	}
	
//	Action a = null;
//	if("insert".equals(action)){
//		a = new InsertAction();
//	}else if("deleteform".equals(action)){
//		a = new DeleteFormAction();
//	}else if("delete".equals(action)){
//			a = new DeleteAction();
//	}else{
//		a = new ListAction();
//	}
//	return a;
}
