package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.service.LogicService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class FlowsIntegration {
    @Bean
    public ExecutorService executorService() {
        //FIXME check Executors.newFixedThreadPool()s
        return Executors.newCachedThreadPool();
    }

    @Bean
    public IntegrationFlow aFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
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
}
