package org.octopus.api.exception;

public class RestError {
	
	private String errorMessage;

	public RestError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
