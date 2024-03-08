package com.onneshon.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private String fieldValueString;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super("Resource '" + resourceName +"' not found with "+fieldName +": "+fieldValue);		
		this.fieldName = fieldName;
		this.resourceName = resourceName;
		this.fieldValue = fieldValue;		
	}	
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super("Resource '" + resourceName +"' not found with "+fieldName +": "+fieldValue);		
		this.fieldName = fieldName;
		this.resourceName = resourceName;
		this.fieldValueString = fieldValue;		
	}
	
}
