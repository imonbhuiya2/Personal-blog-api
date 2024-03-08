package com.onneshon.blog.payloads;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;

@Service
public class ValidationResponse {

	private Map<String, String> response = new HashMap<>();	
	
	
	//for USER
	public Map<String, String> getErrors(Set<ConstraintViolation<UserDto>> violations) {	
		for(ConstraintViolation<?> violation : violations) {			
			String fieldName = violation.getPropertyPath().toString();
			String message = violation.getMessage();			
			response.put(fieldName, message);			
		}		
		return response;				
	}

	
	// for USER
	public Map<String, String> getBlogErrors(Set<ConstraintViolation<BlogDto>> violations) {
		for (ConstraintViolation<?> violation : violations) {
			String fieldName = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			response.put(fieldName, message);
		}
		return response;
	}


}
