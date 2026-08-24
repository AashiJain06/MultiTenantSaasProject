package com.aashi.saas.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TenantNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTenantNotFound(TenantNotFoundException ex)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(TenantAlreadyFoundException.class)
	public ResponseEntity<ErrorResponse> handleTenantAlreadyFound(TenantAlreadyFoundException ex)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.FOUND);
	}
	@ExceptionHandler(ProjectNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProjectNotFound(ProjectNotFoundException ex)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.FOUND);
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex)
	{
		ErrorResponse error = new ErrorResponse(HttpStatus.FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.FOUND);
	}
	
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {

		 ex.printStackTrace();
		
		 	     return ResponseEntity.status(500)
		 	             .body(new ErrorResponse(500, "Something went Wrong"));
	    }
	 @ExceptionHandler(RuntimeException.class)
		public ResponseEntity<ErrorResponse> handleRunTime(RuntimeException ex)
		{
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
			return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
		}



}
