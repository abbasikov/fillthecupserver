package com.macys.exceptions;

public class ServiceException extends Exception{
	
private static final long serialVersionUID = 1L;
	
	private ErrorCodeEnum errorCodeEnum;
	
	public ServiceException(String message, ErrorCodeEnum code) {
		super(message);
		this.errorCodeEnum = code;
	}
	
	public ServiceException(String message, Throwable cause,ErrorCodeEnum code) {
		super(message, cause);
		this.errorCodeEnum = code;
	}
	
	public ErrorCodeEnum getErrorCodeEnum(){
		return errorCodeEnum;
	}
	
}
