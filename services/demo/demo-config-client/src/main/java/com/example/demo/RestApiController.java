package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api")
public class RestApiController {

	private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	@Value("${msg:Hello default}")
    private String message;
	
	@RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
	
	@RequestMapping("/greeting")
	@ResponseBody
	public String greeting() {
		return "Helloworld";
	}

}
