package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.helper.ServiceProperties;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class RestApiController extends BaseApiController {
	@Autowired
	UserService userService;
	//@Autowired
	private final ServiceProperties configuration;

	public RestApiController(ServiceProperties configuration) {
		this.configuration = configuration;
	}
	
	@RequestMapping("/foo")
	@ResponseBody
	public String foo() {
		throw new IllegalArgumentException("Server error");
	}
	@RequestMapping("/hello")
	@ResponseBody
	public String getHelloMessage() {
		return "Hello " + this.configuration.getName();
	}

	@RequestMapping("/greeting")
	@ResponseBody
	public String greeting() {
		return "Helloworld";
	}

	@RequestMapping("/error")
	@ResponseBody
	public String internal() {
		throw new OutOfMemoryError("Internal server error");
	}
}
