package com.flight_booking_system.Configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("FlyBookr API")             
                .version("v1.0")                  
                .description("Flight booking system APIs with JWT security") 
                )
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT") 
                )
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public OpenApiCustomizer securityOpenApiCustomizer() {
        return openApi -> {
            openApi.getPaths().forEach((path, pathItem) -> {
                pathItem.readOperations().forEach(operation -> {
                    if (!path.startsWith("/api/auth") &&
                        !path.startsWith("/swagger-ui") &&
                        !path.startsWith("/v3/api-docs")) {
                        operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
                    }
                    
                });
            });
        };
    }
}
