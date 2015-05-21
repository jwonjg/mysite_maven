package com.sds.icto.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.GuestbookVo;
import com.sds.icto.mysite.exception.GuestbookDaoException;

@Repository
public class GuestbookDao {

	@Autowired
	SqlMapClientTemplate sqlMapClientTemplate;
	
	public void insert(GuestbookVo vo) {
		sqlMapClientTemplate.insert("guestbook.insert", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<GuestbookVo> fetchList() {
		return sqlMapClientTemplate.queryForList("guestbook.selectAll");
	}
	
	public boolean delete(int no, String password) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("password", password);
		return sqlMapClientTemplate.delete("guestbook.delete", map) > 0;
	}
}
