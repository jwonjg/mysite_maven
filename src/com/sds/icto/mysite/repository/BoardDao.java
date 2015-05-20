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

import com.sds.icto.mysite.domain.BoardVo;
import com.sds.icto.mysite.exception.BoardDaoException;

@Repository
public class BoardDao {

	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "webdb", "webdb");
		return connection;
	}
	
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			
			String sql = "insert into board values (BOARD_SEQ.nextval, ?, ?, ?, ?, 0, SYSDATE)";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getUserNo());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getFileName());
			
			int result = ps.executeUpdate();
			if(result>0) System.out.println("저장 성공");
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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
	
	public List<BoardVo> fetchList() {
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			String sql = "select b.no, b.user_no, b.title, b.content, b.file_name, b.clicks, b.reg_date, m.name from board b, member m where b.user_no = m.no";
			
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int no = rs.getInt(1);
				int userNo = rs.getInt(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				String fileName = rs.getString(5);
				int clicks = rs.getInt(6);
				String regDate = rs.getString(7);
				String userName = rs.getString(8);
				
				BoardVo vo = new BoardVo(no, userNo, title, content, fileName, clicks, regDate, userName);
				list.add(vo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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

	public BoardVo selectBoard(int selectNo) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BoardVo vo = null;		
		try{
			conn = getConnection();
			String sql = "select b.no, b.user_no, b.title, b.content, b.file_name, b.clicks, b.reg_date, m.name from board b, member m where b.user_no = m.no and b.no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, selectNo);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int no = rs.getInt(1);
				int userNo = rs.getInt(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				String fileName = rs.getString(5);
				int clicks = rs.getInt(6);
				String regDate = rs.getString(7);
				String userName = rs.getString(8);
				
				vo = new BoardVo(no, userNo, title, content, fileName, clicks, regDate, userName);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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
	
	public boolean delete(int no, int userNo) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("delete from board where no=? and user_no=?");
			ps.setInt(1, no);
			ps.setInt(2, userNo);
	
			result = ps.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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

	public boolean update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("update board set title = ?, content = ?, file_name = ?, reg_date = sysdate where no = ?");
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getContent());
			ps.setString(3, vo.getFileName());
			ps.setInt(4, vo.getNo());
	
			result = ps.executeUpdate() > 0;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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

	public void updateClicks(int no) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("update board set clicks = (select clicks+1 from board where no = ?) where no = ?");
			ps.setInt(1, no);
			ps.setInt(2, no);
		
			ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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

	public List<BoardVo> selectList(String option, String keyword) {
		ArrayList<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			if("title".equals(option)) {
				String sql = "select b.no, b.user_no, b.title, b.content, b.file_name, b.clicks, b.reg_date, m.name from board b, member m where b.user_no = m.no and b.title like ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%"+keyword+"%");
			}else if("content".equals(option)) {
				String sql = "select b.no, b.user_no, b.title, b.content, b.file_name, b.clicks, b.reg_date, m.name from board b, member m where b.user_no = m.no and b.content like ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%"+keyword+"%");
			}else{
				String sql = "select b.no, b.user_no, b.title, b.content, b.file_name, b.clicks, b.reg_date, m.name from board b, member m where b.user_no = m.no and (b.title like ? or b.content like ?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "%"+keyword+"%");
				ps.setString(2, "%"+keyword+"%");
			}
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				int userNo = rs.getInt(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				String fileName = rs.getString(5);
				int clicks = rs.getInt(6);
				String regDate = rs.getString(7);
				String userName = rs.getString(8);
				
				BoardVo vo = new BoardVo(no, userNo, title, content, fileName, clicks, regDate, userName);
				list.add(vo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BoardDaoException(e.getMessage());
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
}
