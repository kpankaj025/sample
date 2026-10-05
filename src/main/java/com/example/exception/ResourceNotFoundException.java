package com.example.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("resource Not found");
	}

	public ResourceNotFoundException(String st) {
		super(st);
	}
	
	

	
}
