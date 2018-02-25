package org.octopus.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Autowired
	private Environment env;

	@Value("classpath:data.sql")
	private Resource dataScript;
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

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(dataScript);
		return populator;
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
	dev, intg, uat, prod, fast
}
