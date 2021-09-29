package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public IntegrationFlow convert() {
        return f -> f
                .transform(Object::toString)
                .handle(String.class, (message, headers) -> new StringBuilder(message).reverse().toString()).
                transform(s -> s.toString().toUpperCase());
    }


}


