package com.codewithpraveen.blog_app_apis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI myCustomConfig() {
        return new OpenAPI().info(new Info().title("Blog App APIs")
                .description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
    }
}
