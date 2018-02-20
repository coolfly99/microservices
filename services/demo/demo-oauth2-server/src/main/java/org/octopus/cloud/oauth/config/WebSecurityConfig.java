package org.octopus.cloud.oauth.config;

import org.octopus.cloud.oauth.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	/*@Bean
	public static PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
		return encoder;
	}*/
	@Override
	@Autowired 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Defining inMemoryAuthentication (2 users)");
		auth.inMemoryAuthentication()
				.withUser("user")
				.password("password")
				//.password("$2a$08$36eCEZKnv59I/BQ/93lewur7eNOZdA2fcpzHi8Lsa1vpaZ7LCZd3W")
				.roles("USER")
				.and()
				.withUser("admin")
				.password("password")
				//.password("$2a$08$36eCEZKnv59I/BQ/93lewur7eNOZdA2fcpzHi8Lsa1vpaZ7LCZd3W")
				.roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()

				.and()

				.httpBasic().disable().anonymous().disable().authorizeRequests().anyRequest().authenticated();
	}
}
