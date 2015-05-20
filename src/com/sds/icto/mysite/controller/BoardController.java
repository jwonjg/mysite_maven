package com.sds.icto.mysite.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sds.icto.mysite.domain.BoardVo;
import com.sds.icto.mysite.domain.CommentVo;
import com.sds.icto.mysite.domain.MemberVo;
import com.sds.icto.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@RequestMapping({"", "/list"})
	public String list(Model model){
		List<BoardVo> list = boardService.getAllList();
		model.addAttribute("list", list);
		return "board/list";
	}

	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(BoardVo vo, @RequestParam(value="file", required=false) MultipartFile multipartFile, HttpServletRequest request, HttpSession session) throws IllegalStateException, IOException{
		MemberVo authMember = (MemberVo)session.getAttribute("authMember");
		if(authMember != null){
			vo.setUserNo(authMember.getNo());
			if(multipartFile != null){
				String saveDir = request.getServletContext().getRealPath("/uploaded_files");
				File file = new File(saveDir+"/"+multipartFile.getOriginalFilename());
				
				if(!file.exists()) multipartFile.transferTo(file);
				else System.out.println("같은 이름의 파일이 존재합니다.");
				vo.setFileName(multipartFile.getOriginalFilename());
			}
			boardService.addBoard(vo);
			return "redirect:/board";
		}else{
			return "redirect:/member/login";
		}
	}
	
	@RequestMapping("/detail/{no}")
	public ModelAndView detail(@PathVariable int no, ModelAndView mav) {
		mav.addObject("board", boardService.getBoard(no));
		mav.addObject("commentList", boardService.getCommentList(no));
		mav.setViewName("board/detail");
		return mav;
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam int no, @RequestParam String fileName, HttpServletRequest request, HttpSession session) {
		boardService.deleteBoard(no, ((MemberVo)session.getAttribute("authMember")).getNo());
		
		String saveDir = "uploaded_files";
		String saveFullDir = request.getServletContext().getRealPath(saveDir);
		if(fileName != null && !fileName.equals("")) new File(saveFullDir+"/"+fileName).delete();
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String updateForm(@PathVariable int no, HttpServletRequest request, Model model) {
		model.addAttribute("board", boardService.getBoard(no));
		return "board/update_form";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(BoardVo board, String prevFileName, @RequestParam(value="file", required=false) MultipartFile multipartFile, HttpServletRequest request) throws IllegalStateException, IOException {
		if(!multipartFile.isEmpty()){
			String saveDir = request.getServletContext().getRealPath("/uploaded_files");
			File file = new File(saveDir+"/"+multipartFile.getOriginalFilename());
			
			if(!file.exists()) multipartFile.transferTo(file);
			else System.out.println("같은 이름의 파일이 존재합니다.");

			board.setFileName(multipartFile.getOriginalFilename());
			if(prevFileName != null && !prevFileName.equals("")) new File(saveDir+"/"+prevFileName).delete();
		}else{
			board.setFileName(prevFileName);
		}
		boardService.updateBoard(board);
		return "redirect:/board/detail/"+board.getNo();
	}
	
	@RequestMapping("/comment")
	public String comment(int boardNo, String content, HttpSession session){
		CommentVo vo = new CommentVo(((MemberVo)session.getAttribute("authMember")).getNo(), content, boardNo);
		boardService.addComment(vo);
		return "redirect:/board/detail/"+boardNo;
	}
	
	@RequestMapping("/deletecomment")
	public String deleteComment(int no, int boardNo, String content, HttpSession session){
		boardService.deleteComment(no, ((MemberVo)session.getAttribute("authMember")).getNo());
		return "redirect:/board/detail/"+boardNo;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(ModelAndView mav, String searchOption, String keyword) {
		List<BoardVo> list = boardService.searchCommentList(searchOption, keyword);
		mav.addObject("list", list);
		mav.setViewName("board/list");
		return mav;
	}
}
