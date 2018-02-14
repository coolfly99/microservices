
package com.example.demo.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
public class ServiceProperties {
	/**
	 * Name of the service.
	 */
	private String name = "World";

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
