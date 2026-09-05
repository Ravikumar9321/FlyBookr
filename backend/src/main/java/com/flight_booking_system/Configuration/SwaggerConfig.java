package com.flight_booking_system.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	  @Bean
	    public OpenAPI flyBookrOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("FlyBookr API")
	                        .description("Flight Booking Management System APIs")
	                        .version("1.0.0"));
	    }
}
