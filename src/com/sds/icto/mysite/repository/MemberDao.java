package com.sds.icto.mysite.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.MemberVo;

@Repository
public class MemberDao {

	@Autowired
	SqlMapClientTemplate sqlMapClientTemplate;
	
	public void insert(MemberVo vo) {
		sqlMapClientTemplate.insert("member.insert", vo);
	}
	
	public MemberVo getMember(String email, String password) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		MemberVo vo = (MemberVo)sqlMapClientTemplate.queryForObject("member.selectMember", map);
		return vo;
	}
	
	public boolean updateMember(MemberVo vo) {
		return sqlMapClientTemplate.update("member.update", vo) > 0;
	}
}
