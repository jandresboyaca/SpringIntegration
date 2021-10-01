package com.deploysoft.cloud.flows;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class QueueIntegration {

    @Bean
    public IntegrationFlow aFlow() {
        return flow -> flow.<Integer>handle((payload, headers) -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return payload * 2;
        });
    }

    @Bean
    public IntegrationFlow bFlow() {
        return flow -> flow.<Integer>handle((payload, headers) -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return payload * 2;
        });
    }

    @Bean
    public IntegrationFlow agg() {
        return BaseIntegrationFlowDefinition::aggregate;
        //return f -> f.aggregate();
    }


    @Bean
    public IntegrationFlow subscribersFlow() {
        return f -> f.publishSubscribeChannel(Executors.newCachedThreadPool(),
                s -> s.applySequence(true)
                        .subscribe(bFlow())
                        .subscribe(aFlow()));
    }

    @Bean
    public IntegrationFlow queueFlow(MessageChannel queueChannel) {
        return IntegrationFlows.from(queueChannel)
                .scatterGather(scatterer -> scatterer
                                .applySequence(true)
                                .recipientFlow(subscribersFlow()),
                        null
                ).get();
    }

  /*  @Bean
    IntegrationFlow flow() throws Exception {
        return IntegrationFlows.from("inputChannel-scatter")
                .publishSubscribeChannel(s -> s.applySequence(true)
                        .subscribe(f -> f
                                .handle(Http.outboundGateway("http://provider1.com/...")
                                        .httpMethod(HttpMethod.GET)
                                        .expectedResponseType(Message[].class))
                                .channel("inputChannel-gather"))
                        .subscribe(f -> f
                                .handle(Http.outboundGateway("http://provider2.com/...")
                                        .httpMethod(HttpMethod.GET)
                                        .expectedResponseType(Message[].class))
                                .channel("inputChannel-gather")))
                .get();
    }*/

}


