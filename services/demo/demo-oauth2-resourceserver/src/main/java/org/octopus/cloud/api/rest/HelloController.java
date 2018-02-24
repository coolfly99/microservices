package org.octopus.cloud.api.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	// Not protected
	@GetMapping("/hello")
	public String helloWorld() {
		return "hello";
	}

	/**
	 * The case for without user , only client, which might sutiable for
	 * client_credentials
	 * 
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('read')")
	@RequestMapping(method = RequestMethod.GET, value = "/greeting")
	public String greeting() {
		return "greeting";
	}
}
