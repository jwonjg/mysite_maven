package com.sds.icto.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.GuestbookVo;
import com.sds.icto.mysite.exception.GuestbookDaoException;

@Repository
public class GuestbookDao {

	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "webdb", "webdb");
		return connection;
	}
	
	public void insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
		
			String sql = "insert into guestbook values (guestbook_SEQ.nextval, ?, ?, ?, SYSDATE)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPassword());
			ps.setString(3, vo.getMessage());
			
			int result = ps.executeUpdate();
			if(result>0) System.out.println("저장 성공");
		} catch (ClassNotFoundException | SQLException e) {
			throw new GuestbookDaoException(e.getMessage());
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
	
	public List<GuestbookVo> fetchList() {
		ArrayList<GuestbookVo> list = new ArrayList<GuestbookVo>();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			String sql = "select * from guestbook";
			
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestbookVo vo = new GuestbookVo(no, name, password, message, regDate);
				list.add(vo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new GuestbookDaoException(e.getMessage());
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(st != null)
				try {
					st.close();
				} catch (SQLException e) {}
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}
		}
		
		return list;
	}
	
	public boolean delete(int no, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = false;
		try{
			conn = getConnection();
			ps = conn.prepareStatement("delete from guestbook where NO=? and password=?");
			ps.setInt(1, no);
			ps.setString(2, password);
			result = ps.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e) {
			throw new GuestbookDaoException(e.getMessage());
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
