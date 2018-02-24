package org.octopus.cloud.oauth;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Inject
	private Environment env;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		addDefaultProfile(app, source);
		Environment env = app.run(args).getEnvironment();
		try {
			log.info("Access URLs:\n----------------------------------------------------------\n\t"
					+ "Local: \t\thttps://127.0.0.1:{} http://127.0.0.1:{}\n\t"
					+ "External: \thttps://{}:{} http://{}:{}\n----------------------------------------------------------",
					env.getProperty("server.port"), env.getProperty("server.http.port"),
					InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
					InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.http.port"));
		} catch (UnknownHostException e) {
			log.error(e.getMessage());
		}
	}

	@PostConstruct
	public void initApplication() throws IOException {
		if (env.getActiveProfiles().length == 0) {
			log.warn("No Spring profile configured, running with default configuration");
		} else {
			log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
			Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
			if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'dev' and 'prod' profiles at the same time.");
			}
			if (activeProfiles.contains("prod") && activeProfiles.contains("fast")) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'prod' and 'fast' profiles at the same time.");
			}
		}
	}

	private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
		if (!source.containsProperty("spring.profiles.active")
				&& !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {
			app.setAdditionalProfiles("dev");
		}
	}
}
