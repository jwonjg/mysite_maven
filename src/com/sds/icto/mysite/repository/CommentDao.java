package com.sds.icto.mysite.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.CommentVo;

@Repository
public class CommentDao {

	@Autowired
	SqlMapClientTemplate sqlMapClientTemplate;
	
	public void insert(CommentVo vo) {
		int orderNo = (int)sqlMapClientTemplate.queryForObject("comment.selectMaxOrderNo", vo.getBoardNo());
		vo.setOrderNo(orderNo);
		sqlMapClientTemplate.insert("comment.insert", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentVo> commentList(int boardNo) {
		return sqlMapClientTemplate.queryForList("comment.selectAll", boardNo);
	}

	public boolean delete(int no, int userNo) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("no", no);
		map.put("userNo", userNo);
		return sqlMapClientTemplate.delete("comment.delete", map) > 0;
	}

}
