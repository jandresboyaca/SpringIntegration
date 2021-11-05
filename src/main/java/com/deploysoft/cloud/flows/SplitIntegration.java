package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.domain.Order;
import com.deploysoft.cloud.domain.TypeFlowEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class SplitIntegration {

    @Bean
    public IntegrationFlow queueSplitFlow(MessageChannel splitterChannel, IntegrationFlow aFlow, IntegrationFlow bFlow, IntegrationFlow cFlow) {
        return IntegrationFlows.from(splitterChannel)
                .split(Order.class, Order::getItems)
                .channel(c -> c.executor(Executors.newCachedThreadPool()))
                .<Item, TypeFlowEnum>route(Item::getType, mapping -> mapping
                        .defaultOutputToParentFlow()
                        .subFlowMapping(TypeFlowEnum.A, aFlow)
                        .subFlowMapping(TypeFlowEnum.B, bFlow)
                        .subFlowMapping(TypeFlowEnum.C, cFlow))
                .aggregate()
                .get();
    }

}


