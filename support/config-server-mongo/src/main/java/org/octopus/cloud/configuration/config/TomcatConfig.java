package org.octopus.cloud.configuration.config;

import org.apache.catalina.connector.Connector;
import org.octopus.cloud.configuration.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TomcatConfig {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Value("${server.http.port}")
	private String httpPort;

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		return tomcat;
	}

	private Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		int port = 0;
		try {
			port = Integer.parseInt(httpPort);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			throw e;
		}
		connector.setPort(port);
		connector.setSecure(false);
		// connector.setRedirectPort(8443);

		return connector;
	}
}
