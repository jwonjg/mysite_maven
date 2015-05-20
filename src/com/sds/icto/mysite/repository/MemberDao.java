package com.sds.icto.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.MemberVo;
import com.sds.icto.mysite.exception.MemberDaoException;

@Repository
public class MemberDao {

	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "webdb", "webdb");
		return connection;
	}
	
	public void insert(MemberVo vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			String sql = "insert into member values(member_no_seq.nextval, ?, ?, ?, ?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getEmail());
			ps.setString(3, vo.getPassword());
			ps.setString(4, vo.getGender());
			
			int result = ps.executeUpdate();
			if(result>0) System.out.println("저장 성공");
		} catch (ClassNotFoundException | SQLException e) {
			throw new MemberDaoException(e.getMessage());
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}
		}
		
	}
	
	public MemberVo getMember(String email, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MemberVo vo = null;

		try {
			conn = getConnection();
			String sql = "select * from member where email=? and password=?";
	
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(3);
				String gender = rs.getString(5);
				
				vo = new MemberVo(no, name, email, password, gender);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new MemberDaoException(e.getMessage());
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}
		}
		
		return vo;
	}
	
	public boolean updateMember(MemberVo vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			conn = getConnection();
			String sql = "update member set email=?, name=?, password=?, gender=? where no=?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getEmail());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getPassword());
			ps.setString(4, vo.getGender());
			ps.setInt(5, vo.getNo());
			
			result = ps.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}
		}
		return result;
	}
}
