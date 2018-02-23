package org.octopus.cloud.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
	}

	/**
	 * An opinionated WebApplicationInitializer to run a SpringApplication from a
	 * traditional WAR deployment. Binds Servlet, Filter and
	 * ServletContextInitializer beans from the application context to the servlet
	 * container.
	 *
	 * @link http://docs.spring.io/spring-boot/docs/current/api/index.html?org/springframework/boot/context/web/SpringBootServletInitializer.html
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	/*
	 * @Configuration
	 * 
	 * @EnableWebSecurity
	 * 
	 * @EnableGlobalMethodSecurity(prePostEnabled = true) protected static class
	 * SecurityConfig extends WebSecurityConfigurerAdapter {
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception { http
	 * .authorizeRequests().anyRequest().authenticated() .and()
	 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER) ; } }
	 */

	/*
	 * @Bean public JwtAccessTokenConverter accessTokenConverter() {
	 * JwtAccessTokenConverter converter = new JwtAccessTokenConverter(); Resource
	 * resource = new ClassPathResource("public.txt"); String publicKey = null; try
	 * { publicKey = IOUtils.toString(resource.getInputStream()); } catch (final
	 * IOException e) { throw new RuntimeException(e); }
	 * converter.setVerifierKey(publicKey); return converter; }
	 */
}