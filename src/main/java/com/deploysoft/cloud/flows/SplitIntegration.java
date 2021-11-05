package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.domain.Order;
import com.deploysoft.cloud.domain.TypeFlowEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class SplitIntegration {

    @Bean
    public ExecutorService executorServiceSplitter() {
        //FIXME check Executors.newFixedThreadPool()
        return Executors.newCachedThreadPool();
    }

    @Bean
    public IntegrationFlow queueSplitFlow(MessageChannel splitterChannel, IntegrationFlow aFlow, IntegrationFlow bFlow, IntegrationFlow cFlow) {
        return IntegrationFlows.from(splitterChannel)
                .split(Order.class, Order::getItems)
                .channel(c -> c.executor(executorServiceSplitter()))
                .<Item, TypeFlowEnum>route(Item::getType, mapping -> mapping
                                .subFlowMapping(TypeFlowEnum.A, sf -> sf
                                        .channel(c -> c.queue(10))
                                        .publishSubscribeChannel(c -> c.subscribe(aFlow))
                                        .aggregate()
                                        .bridge())
                        /*.subFlowMapping(TypeFlowEnum.B, sf -> sf
                                .channel(c -> c.queue(10))
                                .publishSubscribeChannel(c -> c
                                        .subscribe(bFlow))
                                .bridge())
                        .subFlowMapping(TypeFlowEnum.B, sf -> sf
                                .channel(c -> c.queue(10))
                                .publishSubscribeChannel(c -> c
                                        .subscribe(cFlow))
                                .bridge()))*/
                ).aggregate().get();
    }

}


