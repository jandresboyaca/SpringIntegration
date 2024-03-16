package com.deploysoft.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;

import java.util.function.Consumer;

@Slf4j
@EnableIntegration
@SpringBootApplication
public class SpringIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationApplication.class, args);
    }


    @Bean
    Consumer<String> sink(){
        return System.out::println;
    }

}
