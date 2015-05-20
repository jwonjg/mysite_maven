package com.sds.icto.mysite.exception;

public class BoardDaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoardDaoException() {
		super("Board Dao Exception");
	}
	
	public BoardDaoException(String message) {
		super(message);
	}
}
