package org.octopus.cloud.oauth;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	// @formatter:off
	/*
	 * f They way to auto redirect
	 * 
	 * @Bean public TomcatServletWebServerFactory servletContainer() {
	 * TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	 * 
	 * @Override protected void postProcessContext(Context context) {
	 * SecurityConstraint securityConstraint = new SecurityConstraint();
	 * securityConstraint.setUserConstraint("CONFIDENTIAL"); SecurityCollection
	 * collection = new SecurityCollection(); collection.addPattern("/*");
	 * securityConstraint.addCollection(collection);
	 * context.addConstraint(securityConstraint); } };
	 * 
	 * tomcat.addAdditionalTomcatConnectors(redirectConnector()); return tomcat; }
	 * 
	 * private Connector redirectConnector() { Connector connector = new
	 * Connector("org.apache.coyote.http11.Http11NioProtocol");
	 * connector.setScheme("http"); connector.setPort(8080);
	 * connector.setSecure(false); connector.setRedirectPort(8443);
	 * 
	 * return connector; }
	 */
	// @formatter:on*/
	@Value("${server.http.port}")
	private String httpPort;
	@Bean
    public TomcatServletWebServerFactory servletContainer(){
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
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
