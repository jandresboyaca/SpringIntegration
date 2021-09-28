package com.deploysoft.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
public class SpringCloudApplication {

    @Bean
    public Function<String, String> uppercase() {
        return  s -> {
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

    @Bean
    public Function<Flux<String>, Flux<String>> reverseReactive() {
        return flux -> flux.map(String::toUpperCase);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

/*    @Bean
    public IntegrationFlow uppercaseFlow() {
        return IntegrationFlows.from(MessageFunction.class, (gateway) -> gateway.beanName("uppercase"))
                .<String, String>transform(String::toUpperCase)
                .logAndReply(LoggingHandler.Level.WARN);
    }

    public interface MessageFunction extends Function<Message<String>, Message<String>> {

    }*/




  /*  @Bean
    CommandLineRunner runner(Action action) {
        return args -> action.sendToMyChannel("prueba");
    }*/


}
