package org.octopus.cloud.configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.mongodb.EnableMongoConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@EnableMongoConfigServer
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

	@Autowired
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
		log.info("Connected to RabbitMQ at: {}:{}", env.getProperty("spring.rabbitmq.host"),
				env.getProperty("spring.rabbitmq.port"));
	}

	@PostConstruct
	public void initApplication() throws IOException {
		if (env.getActiveProfiles().length == 0) {
			log.warn("No Spring profile configured, running with default configuration");
		} else {
			log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
			Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
			if (activeProfiles.contains(Profile.dev.toString()) && activeProfiles.contains(Profile.prod.toString())) {
				log.error("You have misconfigured your application! " + "It should not run with both the '"
						+ Profile.dev.toString() + "' and '" + Profile.prod.toString()
						+ "' profiles at the same time.");
			}
			if (activeProfiles.contains(Profile.prod.toString()) && activeProfiles.contains(Profile.fast.toString())) {
				log.error("You have misconfigured your application! " + "It should not run with both the 'prod' and '"
						+ Profile.fast.toString() + "' profiles at the same time.");
			}
		}
	}

	private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
		if (!source.containsProperty("spring.profiles.active")
				&& !System.getenv().containsKey("spring.profiles.active")) {
			app.setAdditionalProfiles(Profile.dev.toString());
		}
	}
}

enum Profile {
	dev, intg, uat, prod, fast, jdbc// add jdbc with configration
}
