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
            // String queueMessage = producer.queueChannel(Message.builder().message("a").build());
            // log.info("Response from flow [{}]", queueMessage);

          /*  long startTime = System.currentTimeMillis();
            log.warn("Inicio {}", startTime);
            log.info(producer.queueChannel((Message.builder().message("a").build())));
            long endTime = System.currentTimeMillis();
            log.warn("Fin {}", endTime - startTime);*/

            long startTime2 = System.currentTimeMillis();
            log.warn("Inicio {}", startTime2);
            log.info("aca" + producer.testPush(2));
            long endTime2 = System.currentTimeMillis();
            log.warn("Fin {}", endTime2 - startTime2);
        };
    }
}
