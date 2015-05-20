package com.sds.icto.mysite.domain;

public class CommentVo {
	private int no;
	private int userNo;
	private String content;
	private String regDate;
	private int boardNo;
	private int orderNo;
	private String userName;
	
	public CommentVo() {}

	public CommentVo(int userNo, String content, int boardNo) {
		super();
		this.userNo = userNo;
		this.content = content;
		this.boardNo = boardNo;
	}

	public CommentVo(int no, int userNo, String content, String regDate,
			int boardNo, int orderNo, String userName) {
		super();
		this.no = no;
		this.userNo = userNo;
		this.content = content;
		this.regDate = regDate;
		this.boardNo = boardNo;
		this.orderNo = orderNo;
		this.userName = userName;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}