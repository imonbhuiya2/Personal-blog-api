package com.onneshon.blog.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	
	//validation error handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler (MethodArgumentNotValidException ex){
		
		Map<String, String> response = new HashMap<>(); 
		response.put("ValidationError", "Invalid Input");
		
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		
		for(ObjectError error : allErrors) {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();			
			response.put(fieldName, message);
		}		
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);		
	}
	
	
	
	
	//resource not found Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> resourceNotFoundExceptionHandler(
			ResourceNotFoundException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("error", ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.NOT_FOUND);
	}
	
	
	//Parameter missing thakle
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Map<String, String>> handelMissingServletRequestParameterException(
			MissingServletRequestParameterException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("ParameterMissing", ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	
	//BadCredentialsException
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> handelBadCredentialsException(
			BadCredentialsException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("BadCredentials", ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	
	//User Not Found Exception
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Map<String, String>> handelUsernameNotFoundException(
			UsernameNotFoundException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("UserNotFound", ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	//Session Expired
	@ExceptionHandler(JwtSessionExpiredException.class)
	public ResponseEntity<Map<String, String>> handleJwtSessionExpiredException(
			JwtSessionExpiredException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("SessionExpired", ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.FORBIDDEN);
	}
	

	
	//multipart file send na korle
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<Map<String, String>> handleMissingServletRequestPartException(
			MissingServletRequestPartException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("ImageMissing", "Please! Insert Image"+ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.FORBIDDEN);
	}
	
	
	
	//if user is not logged in
	@ExceptionHandler(UserNotLoggedInException.class)
	public ResponseEntity<Map<String, String>> handleUserNotLoggedInException(
			UserNotLoggedInException ex) {

		Map<String, String> response = new HashMap<>();		

		response.put("error", ""+ex.getMessage());
		
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.FORBIDDEN);
	}
	
	
}