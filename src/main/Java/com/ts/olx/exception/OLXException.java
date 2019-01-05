package com.ts.olx.exception;

public class OLXException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public OLXException(String message){
		this.message = message; 
	}
	
	@Override
	public String toString(){
		return message;
	}

}
