package com.sds.icto.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sds.icto.mysite.domain.CommentVo;
import com.sds.icto.mysite.exception.CommentDaoException;

@Repository
public class CommentDao {

	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "webdb", "webdb");
		return connection;
	}
	
	public void insert(CommentVo vo) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
	
			String sql = "select max(order_no) from board_comment where board_no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getBoardNo());
			rs = ps.executeQuery();
			rs.next();
			int orderNo = rs.getInt(1);
			ps.close();
			
			sql = "insert into board_comment values (BOARD_COMMENT_SEQ.nextval, ?, ?, SYSDATE, ?, ?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, vo.getUserNo());
			ps.setString(2, vo.getContent());
			ps.setInt(3, vo.getBoardNo());
			ps.setInt(4, ++orderNo);
			
			int result = ps.executeUpdate();
			if(result>0) System.out.println("저장 성공");
		} catch (ClassNotFoundException | SQLException e) {
			throw new CommentDaoException(e.getMessage());
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
	}
	
	public List<CommentVo> commentList(int boardNo) {
		ArrayList<CommentVo> list = new ArrayList<CommentVo>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select c.no, c.user_no, c.content, c.reg_date, c.order_no, m.name from board_comment c, member m where c.user_no = m.no and c.board_no = ? order by order_no";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, boardNo);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int no = rs.getInt(1);
				int userNo = rs.getInt(2);
				String content = rs.getString(3);
				String regDate = rs.getString(4);
				int orderNo = rs.getInt(5);
				String userName = rs.getString(6);
				
				CommentVo vo = new CommentVo(no, userNo, content, regDate, boardNo, orderNo, userName);
				list.add(vo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new CommentDaoException(e.getMessage());
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
		
		return list;
	}

	public boolean delete(int no, int userNo) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("delete from board_comment where no=? and user_no=?");
			ps.setInt(1, no);
			ps.setInt(2, userNo);
	
			result = ps.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e) {
			throw new CommentDaoException(e.getMessage());
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
