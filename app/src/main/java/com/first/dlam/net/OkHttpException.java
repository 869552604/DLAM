package com.first.dlam.net;

/**
 * �Զ����exception
 * 
 * @author MKW
 */
public class OkHttpException extends Exception {
	private static final long serialVersionUID = 1L;

	public OkHttpException() {
		super();
	}

	public OkHttpException(String message) {
		super(message);
	}

	public OkHttpException(String message, Throwable cause) {
		super(message, cause);
	}

	public OkHttpException(Throwable cause) {
		super(cause);
	}
}
