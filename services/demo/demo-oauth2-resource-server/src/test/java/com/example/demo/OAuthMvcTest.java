package com.example.demo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.cloud.api.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@ActiveProfiles("mvc")
public class OAuthMvcTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;

	private static final String CLIENT_ID = "trusted";
	private static final String CLIENT_SECRET = "secret";

	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	private static final String EMAIL = "ohmylove9928@gmail.com";
	private static final String NAME = "Ives";

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	}

	private String obtainToken(String username, String password) throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", CLIENT_ID);
		params.add("username", username);
		params.add("password", password);

		// @formatter:off

		ResultActions result = mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(CLIENT_ID, CLIENT_SECRET))
						.accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE));

		// @formatter:on

		String resultString = result.andReturn().getResponse().getContentAsString();
		System.out.println("resultString:" + resultString);
		return resultString;
	}

	private String obtainRefreshToken(String username, String password, String refresh_token) throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "refresh_token");
		params.add("client_id", CLIENT_ID);
		params.add("username", username);
		params.add("password", password);
		params.add("refresh_token", refresh_token);

		// @formatter:off

		ResultActions result = mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(CLIENT_ID, CLIENT_SECRET))
						.accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE));

		// @formatter:on

		String resultString = result.andReturn().getResponse().getContentAsString();
		System.out.println("resultString:" + resultString);
		return resultString;
	}

	private String getAccessToken(String response) {
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(response).get("access_token").toString();
	}

	private String getRefreshToken(String response) {
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(response).get("refresh_token").toString();
	}

	@Test
	public void test() throws Exception {
		final String token = obtainToken("user", "password");
		System.out.println("token:" + getAccessToken(token));
		final String currentRefreshToken = getRefreshToken(token);
		System.out.println("refreshtoken:" + currentRefreshToken);
		final String newRefreshToken = obtainRefreshToken("user", "password", currentRefreshToken);
		System.out.println("newRefreshtoken:" + newRefreshToken);
	}

}