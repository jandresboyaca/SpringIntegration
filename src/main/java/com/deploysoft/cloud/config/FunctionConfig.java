package com.deploysoft.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class FunctionConfig {

    @Bean
    public Function<String, String> uppercase() {
        return s -> {
            log.info("uppercase from spring function...");
            return s.toUpperCase();
        };
    }

    @Bean
    public Function<String, String> reverse() {
        return message -> {
            log.info("reversing from spring function...");
            return new StringBuilder(message).reverse().toString();
        };
    }
}
