package com.aashi.saas.exception;

public class ProjectMemberAlreadyExistsException extends RuntimeException
{
   public ProjectMemberAlreadyExistsException(String message) {
	   super(message);
   }
}
