package com.deploysoft.cloud;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.domain.Order;
import com.deploysoft.cloud.domain.TypeFlowEnum;
import com.deploysoft.cloud.gateway.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.EnableIntegration;

import java.util.List;
import java.util.Map;

@Slf4j
@EnableIntegration
@SpringBootApplication
public class SpringIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(Producer producer) {
        return args -> {
            Item b = producer.testRouter(new Item(TypeFlowEnum.B,"",""));
            log.warn("end of flow B {}", b);

            Item a = producer.testRouter(new Item(TypeFlowEnum.A,"",""));
            log.warn("end of flow A {}", a);
        };
    }

    private void scatterGather(Producer producer) {
        long startTime = System.currentTimeMillis();
        log.warn("Init {}", startTime);
        Item message = Item.builder().config("test").message("message").build();
        Map<String, Object> headers = Map.of("InitialTime", System.currentTimeMillis());
        List<String> arg = producer.queueChannel(message, headers);
        log.info("Response from flow {}", arg);
        long endTime = System.currentTimeMillis();
        log.warn("End {}", endTime - startTime);
    }

    private void splitter(Producer producer) {
        long startTime = System.currentTimeMillis();
        log.warn("Init {}", startTime);
        Item message1 = new Item(TypeFlowEnum.A, "", "");
        Item message2 = new Item(TypeFlowEnum.A, "", "");
        Item message3 = new Item(TypeFlowEnum.B, "", "");
        Item message4 = new Item(TypeFlowEnum.C, "", "");
        Map<String, Object> headers = Map.of("InitialTime", System.currentTimeMillis());
        List<String> arg = producer.splitterChannel(new Order(List.of(message1, message2, message3)), headers);
        log.info("Response from flow {}", arg);
        long endTime = System.currentTimeMillis();
        log.warn("End {}", endTime - startTime);
    }

}
