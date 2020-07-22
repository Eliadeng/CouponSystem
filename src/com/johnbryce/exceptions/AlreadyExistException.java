package com.johnbryce.exceptions;

public class AlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistException() {
		super("The company is already exits");
	}

	public AlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistException(String message, String string) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
