package com.deploysoft.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;

import java.util.function.Function;

@SpringBootApplication
public class IntegrationApplication {

    @Bean
    Function<String, String> reverse() {
        return s -> new StringBuilder(s).reverse().toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }

    @Bean
    public IntegrationFlow uppercaseFlow() {
        return IntegrationFlows.from(MessageFunction.class, (gateway) -> gateway.beanName("uppercase"))
                .<String, String>transform(String::toUpperCase)
                .logAndReply(LoggingHandler.Level.WARN);
    }

    public interface MessageFunction extends Function<Message<String>, Message<String>> {

    }




  /*  @Bean
    CommandLineRunner runner(Action action) {
        return args -> action.sendToMyChannel("prueba");
    }*/


}
