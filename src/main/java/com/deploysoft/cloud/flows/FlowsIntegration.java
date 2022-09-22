package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.Order;
import com.deploysoft.cloud.service.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class FlowsIntegration {
    @Bean
    public ExecutorService executorService() {
        //FIXME check Executors.newFixedThreadPool()
        return Executors.newCachedThreadPool();
    }

    @Bean
    public IntegrationFlow aFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
                .log(LoggingHandler.Level.WARN)
                .handle(service::callFakeServiceTimeout5)
                .transform((String.class), String::toLowerCase).get();
    }

    @Bean
    public IntegrationFlow bFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
                .handle(service::callFakeServiceTimeout10)
                .transform((String.class), String::toUpperCase).get();
    }

    @Bean
    public IntegrationFlow cFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
                .handle(service::callFakeServiceTimeout20)
                .transform((String.class), String::length).get();
    }


    @Bean
    public IntegrationFlow executorTest() {
        return IntegrationFlows.from("testChannelExecutor")
                .handle(Order.class, (m, h) -> {
                    log.info("check");
                    return null;
                })
                .get();
    }
}
