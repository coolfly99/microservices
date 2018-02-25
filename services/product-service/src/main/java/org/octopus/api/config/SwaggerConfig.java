package org.octopus.api.config;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@Import(SpringDataRestConfiguration.class) 
//This seems not support Reference:https://github.com/springfox/springfox/issues/1957
@EnableAutoConfiguration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.tags(new Tag("product-controller", "Repository for Product entities")).select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/rest/.*|/api/user.*|/api/register.*|/oauth/token.*"))
				// PathSelectors.any() for all
				.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo("Product Apis", "This Api provide product information CRUD", "1.0", "urn:tos",
				new Contact("Jason Leng", "http://www.octopus.org", ""), "Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<VendorExtension>());
		return apiInfo;
	}
}