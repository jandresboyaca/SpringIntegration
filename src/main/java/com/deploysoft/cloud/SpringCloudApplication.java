package com.deploysoft.cloud;

import com.deploysoft.cloud.domain.Message;
import com.deploysoft.cloud.gateway.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(Producer producer) {
        return args -> {
            String mymessage = producer.produceAndConsume("message");
            String message = producer.produceAndConsume(new Message());
            String queueMessage = producer.queueChannel(new Message());
            log.info(queueMessage);
            log.info(message);
            log.info(mymessage);
        };
    }
}
