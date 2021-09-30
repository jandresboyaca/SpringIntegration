package com.deploysoft.cloud;

import com.deploysoft.cloud.domain.Message;
import com.deploysoft.cloud.gateway.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@Slf4j
@SpringBootApplication
public class SpringCloudApplication {

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

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(Producer producer) {
        return args -> {
            String mymessage = producer.produceAndConsume("message");
            String message = producer.produceAndConsume(new Message());
            String queueMessage = producer.queueChannel(new Message());

            System.out.println(queueMessage);
            System.out.println(message);
            System.out.println(mymessage);
        };
    }
}
