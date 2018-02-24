package com.example.demo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.cloud.api.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBodyExtractionOptions;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@Autowired
	private JwtTokenStore tokenStore;

	@LocalServerPort
	int randomServerPort;

	@Test
	public void test() throws KeyManagementException, UnrecoverableKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		final String tokenValue = obtainAccessToken("trusted", "user", "password");
		final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
		System.out.println(tokenValue);
		System.out.println(auth);
		assertTrue(auth.isAuthenticated());
		System.out.println(auth.getDetails());

		Map<String, Object> details = (Map<String, Object>) auth.getDetails();
		// assertTrue(details.containsKey("organization"));
		System.out.println(details.get("organization"));
	}

	private String obtainAccessToken(String clientId, String username, String password)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			KeyManagementException, UnrecoverableKeyException {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);

		KeyStore keyStore = null;
		SSLConfig config = null;

		/*
		 * try { keyStore = KeyStore.getInstance("PKCS12"); final Resource resource =
		 * new ClassPathResource("keystore.p12");
		 * keyStore.load(resource.getInputStream(), "password".toCharArray());
		 * 
		 * } catch (Exception ex) {
		 * System.out.println("Error while loading keystore >>>>>>>>>");
		 * ex.printStackTrace(); }
		 * 
		 * try { keyStore = KeyStore.getInstance("PKCS12"); keyStore.load( new
		 * FileInputStream("certs/client_cert_and_private.p12"),
		 * password.toCharArray());
		 * 
		 * } catch (Exception ex) {
		 * System.out.println("Error while loading keystore >>>>>>>>>");
		 * ex.printStackTrace(); }
		 */
		/*CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		String urlOverHttps = "https://localhost:" + randomServerPort + "/api/hello";
		HttpGet getMethod = new HttpGet(urlOverHttps);

		HttpResponse response = httpClient.execute(getMethod);

		keyStore = KeyStore.getInstance("PKCS12");
		final Resource resource = new ClassPathResource("keystore.p12");
		keyStore.load(resource.getInputStream(), "password".toCharArray());
		org.apache.http.conn.ssl.SSLSocketFactory clientAuthFactory = new org.apache.http.conn.ssl.SSLSocketFactory(
				keyStore, password);

		// set the config in rest assured
		config = new SSLConfig().with().sslSocketFactory(clientAuthFactory).and().allowAllHostnames();

		RestAssured.config = RestAssured.config().sslConfig(config);
		// RestAssured.given().when().get("/path").then();*/

		final Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with()
				.params(params).when()
				.post("http://localhost:8081/oauth/token");
				//.post("https://localhost:8443/oauth/token"); not work?
		return response.jsonPath().getString("access_token");
	}
}
