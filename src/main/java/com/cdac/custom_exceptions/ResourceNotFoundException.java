package com.cdac.custom_exceptions;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String mesg) {
		super(mesg);
	}
	
	 public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
	        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
	    }
}
