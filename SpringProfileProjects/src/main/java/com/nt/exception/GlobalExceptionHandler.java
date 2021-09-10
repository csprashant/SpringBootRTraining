package com.nt.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<?> StudentNotFoundExceptionHandling(StudentNotFoundException snfe,WebRequest request){
		System.out.println("StudentNotFoundExceptionHandling");
		ErrorDetails details=new ErrorDetails(new Date(),snfe.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException manve){
		System.out.println("GlobalExceptionHandler.customValidationErrorHandling()");
		ErrorDetails details=new ErrorDetails(new Date(),"Validation Error",manve.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(details,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> glabalExceptionHandling(Exception exception,WebRequest request){
		ErrorDetails details=new ErrorDetails(new Date(),exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(details,HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
