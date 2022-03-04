package com.selenium.ui.helper;

public class ExceptionHelper extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ExceptionHelper() {
		
	}

	public ExceptionHelper(String message) {
		super(message);
	}
	
	public ExceptionHelper(Throwable cause) {
		super(cause);
	}
	
	public ExceptionHelper(String message,Throwable cause) {
		super(cause);
	}
}
