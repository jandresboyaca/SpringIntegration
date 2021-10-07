package com.deploysoft.cloud;

import com.deploysoft.cloud.domain.MessageDomain;
import com.deploysoft.cloud.gateway.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Map;

@Slf4j
@EnableIntegration
@EnableScheduling
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(Producer producer) {
        return args -> {
            long startTime = System.currentTimeMillis();
            log.warn("Init {}", startTime);
            MessageDomain message = MessageDomain.builder().config("test").message("message").build();
            Map<String, Object> headers = Map.of("Key", "Value");
            List<MessageDomain> arg = producer.queueChannel(message, headers);
            log.info("Response from flow {}", arg);
            long endTime = System.currentTimeMillis();
            log.warn("End {}", endTime - startTime);
        };
    }
/*
    @ServiceActivator(inputChannel = "storeChannel.out")
    public void test(Message o) {
        System.out.println(o);
    }
*/
}
