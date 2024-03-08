package com.onneshon.blog.exceptions;

public class JwtSessionExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public JwtSessionExpiredException(String message) {
		super(message);		
	}
}
