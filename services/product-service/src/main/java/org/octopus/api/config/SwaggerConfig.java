package org.octopus.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
// @Import(SpringDataRestConfiguration.class)
// This seems not support
// Reference:https://github.com/springfox/springfox/issues/1957
@EnableAutoConfiguration
public class SwaggerConfig {

	@Value("${app.oauth.clientId}")
	private String clientId;
	@Value("${app.oauth.clientSecret}")
	private String clientSecret;
	@Value("${app.oauth.url}")
	private String authUrl;

	@Bean
	public Docket api() {
		List<ResponseMessage> list = new java.util.ArrayList<>();
		list.add(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Result"))
				.build());
		list.add(new ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(new ModelRef("Result"))
				.build());
		list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable").responseModel(new ModelRef("Result"))
				.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.tags(new Tag("product-controller", "Repository for Product entities")).select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/api.*|/rest/.*|/api/user.*|/api/register.*|/oauth/token.*"))
				// PathSelectors.any() for all
				.build().securitySchemes(apiKeys())
				.securityContexts(securityContext()).apiInfo(apiInfo());
				//.globalResponseMessage(RequestMethod.GET, list).globalResponseMessage(RequestMethod.POST, list);
	}

	private ApiInfo apiInfo() {
		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo("Product Apis", "This Api provide product information CRUD", "1.0", "urn:tos",
				new Contact("Jason Leng", "http://www.octopus.org", ""), "Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
		return apiInfo;
	}
	
	private List<SecurityContext> securityContext() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        SecurityContext context = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/**"))
                .build();
        securityContexts.add(context);
        return  securityContexts;
    }
    List<SecurityReference> defaultAuth() {
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
        authorizationScopes[0] = new AuthorizationScope( "read", "read only" );
        authorizationScopes[1] = new AuthorizationScope( "write", "read and write" );
        List<SecurityReference> list=  new ArrayList<SecurityReference>();
        SecurityReference s = new SecurityReference("Authorization", authorizationScopes);
        list.add(s);
        return list;
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(
                "client_id",
                "client_secret",
                "realm",
                "XXX",
                "Bearer ",
                ApiKeyVehicle.HEADER,
                "Authorization",
                ",");
    }

    private List<SecurityScheme> apiKeys() {
        ArrayList<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("Authorization", "Authorization", "header"));
        return securitySchemes;
    }

	/*private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = new ArrayList();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));

		List<GrantType> grantTypes = new ArrayList();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authUrl);

		grantTypes.add(creGrant);

		return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

	}*/

	/*private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/**"))
				.build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("write", "write all");

		return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
	}
	
	*/

	/*@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
	}*/
	
	/*@Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER,"Authorization",": Bearer");
    }*/
	/*
	 * private SecurityScheme securityScheme() { GrantType grantType = new
	 * AuthorizationCodeGrantBuilder() .tokenEndpoint(new TokenEndpoint(AUTH_SERVER
	 * + "/token", "oauthtoken")) .tokenRequestEndpoint( new
	 * TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_ID))
	 * .build();
	 * 
	 * SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
	 * .grantTypes(Arrays.asList(grantType)) .scopes(Arrays.asList(scopes()))
	 * .build(); return oauth; }
	 */
}