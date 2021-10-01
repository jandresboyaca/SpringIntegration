package com.deploysoft.cloud;

import com.deploysoft.cloud.domain.Message;
import com.deploysoft.cloud.gateway.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;

@Slf4j
@EnableIntegration
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
            log.info("Response from flow [{}]", producer.queueChannel(2));
            long endTime = System.currentTimeMillis();
            log.warn("End {}", endTime - startTime);
        };
    }
}
