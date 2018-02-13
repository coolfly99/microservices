package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseApiController {

	private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@ExceptionHandler
	public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response)
			throws IOException {
		logger.error("handleIllegalArgumentException():" + e.getMessage());
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler
	public void handleOutOfMemoryError(OutOfMemoryError e, HttpServletResponse response) throws IOException {
		logger.error("handleOutOfMemoryError():" + e.getMessage());
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
