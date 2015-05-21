package com.sds.icto.mysite.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	SqlMapClientTemplate sqlMapClientTemplate;
	
	public void insert(BoardVo vo) {
		sqlMapClientTemplate.insert("board.insert", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<BoardVo> fetchList() {
		return sqlMapClientTemplate.queryForList("board.selectAll");
	}

	public BoardVo selectBoard(int selectNo) {
		return (BoardVo)sqlMapClientTemplate.queryForObject("board.selectBoard", selectNo);
	}
	
	public boolean delete(int no, int userNo) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("no", no);
		map.put("userNo", userNo);
		return sqlMapClientTemplate.delete("board.delete", map) > 0;
	}

	public boolean update(BoardVo vo) {
		return sqlMapClientTemplate.update("board.update", vo) > 0;
	}

	public void updateClicks(int no) {
		sqlMapClientTemplate.update("board.updateClick", no);
	}

	@SuppressWarnings("unchecked")
	public List<BoardVo> selectList(String option, String keyword) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("option", option);
		map.put("keyword", keyword);
		return sqlMapClientTemplate.queryForList("board.search", map);
	}
}
