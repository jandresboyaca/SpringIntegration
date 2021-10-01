package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.service.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class QueueIntegration {

    @Bean
    public IntegrationFlow aFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(Executors.newCachedThreadPool()))
                .log()
                .handle(service::callFakeService)
                .get();
    }

    @Bean
    public IntegrationFlow bFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(Executors.newCachedThreadPool()))
                .log()
                .handle(service::callFakeService)
                .get();
    }

    @Bean
    public IntegrationFlow queueFlow(MessageChannel queueChannel, LogicService service) {
        return IntegrationFlows.from(queueChannel)
                .scatterGather(scatterer -> scatterer
                                .applySequence(true)
                                .recipientFlow(aFlow(service))
                                .recipientFlow(bFlow(service))
                        ,
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


