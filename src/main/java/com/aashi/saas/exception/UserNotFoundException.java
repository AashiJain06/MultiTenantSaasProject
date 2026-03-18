package com.aashi.saas.exception;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message)
	{
		super(message);
	}

}
