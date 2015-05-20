package com.sds.icto.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.icto.mysite.domain.BoardVo;
import com.sds.icto.mysite.domain.CommentVo;
import com.sds.icto.mysite.repository.BoardDao;
import com.sds.icto.mysite.repository.CommentDao;

@Service
public class BoardService {

	@Autowired
	BoardDao boardDao;
	
	@Autowired
	CommentDao commentDao;

	public List<BoardVo> getAllList() {
		return boardDao.fetchList();
	}

	public void addBoard(BoardVo vo) {
		boardDao.insert(vo);
	}

	public BoardVo getBoard(int no) {
		boardDao.updateClicks(no);
		return boardDao.selectBoard(no);
	}
	
	public List<CommentVo> getCommentList(int no) {
		return commentDao.commentList(no);
	}

	public void deleteBoard(int no, int memberNo) {
		boardDao.delete(no, memberNo);
	}

	public void updateBoard(BoardVo board) {
		boardDao.update(board);
	}

	public void addComment(CommentVo vo) {
		commentDao.insert(vo);
	}

	public void deleteComment(int no, int memberNo) {
		commentDao.delete(no, memberNo);
	}

	public List<BoardVo> searchCommentList(String searchOption, String keyword) {
		return boardDao.selectList(searchOption, keyword);
	}
	
}
