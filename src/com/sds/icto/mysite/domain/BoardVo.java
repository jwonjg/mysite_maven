package com.sds.icto.mysite.domain;

public class BoardVo {
	private int no;
	private int userNo;
	private String title;
	private String content;
	private String fileName;
	private int clicks;
	private String regDate;
	
	private String userName;

	public BoardVo() {}

	public BoardVo(int userNo, String title, String content, String fileName) {
		super();
		this.userNo = userNo;
		this.title = title;
		this.content = content;
		this.fileName = fileName;
	}

	public BoardVo(int no, int userNo, String title, String content,
			String fileName, int clicks, String regDate, String userName) {
		super();
		this.no = no;
		this.userNo = userNo;
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.clicks = clicks;
		this.regDate = regDate;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	
}
