package com.api.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

	String schemeName = "bearerScheme";

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(schemeName))
				.components(new Components().addSecuritySchemes(schemeName,
						new SecurityScheme().name(schemeName).type(Type.HTTP).bearerFormat("JWT").scheme("bearer")))
				.info(new Info().title("Blog Api").description("This is an Blog api developed by manish devlikar")
						.version("v0.0.1").license(new License().name("Apache 2.0").url(""))
						.contact(new Contact().name("manish").email("manish.devlikar5003@gmmail.com")))
				.externalDocs(new ExternalDocumentation().description("Blog api  Documentation").url(""));
	}

}
