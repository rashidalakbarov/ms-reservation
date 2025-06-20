package com.barber.reservation.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configureOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Barber Reservation API")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("Barber Reservation"));
    }
}