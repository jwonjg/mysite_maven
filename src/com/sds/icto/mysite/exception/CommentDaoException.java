package com.sds.icto.mysite.exception;

public class CommentDaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CommentDaoException() {
		super("Comment Dao Exception");
	}
	
	public CommentDaoException(String message) {
		super(message);
	}
}
