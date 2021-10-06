package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.MessageDomain;
import com.deploysoft.cloud.service.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class QueueIntegration {

    @Bean
    public IntegrationFlow aFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(Executors.newCachedThreadPool()))
                .handle(service::callFakeServiceTimeout2)
                .transform((MessageDomain.class), message -> {
                    message.setMessage(new StringBuilder(message.getMessage()).reverse().toString());
                    return message;
                })
                .get();
    }

    @Bean
    public IntegrationFlow bFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(Executors.newCachedThreadPool()))
                .handle(service::callFakeServiceTimeout4)
                .transform((MessageDomain.class), message -> {
                    message.setMessage(message.getMessage().toUpperCase());
                    return message;
                }).get();
    }

    @Bean
    public IntegrationFlow queueFlow(LogicService service) {
        return f -> f
                .scatterGather(scatterer -> scatterer
                                .applySequence(true)
                                .recipientFlow(aFlow(service))
                                .recipientFlow(bFlow(service)),
                        gatherer -> gatherer
                                .sendPartialResultOnExpiry(true)
                        , scatterGather -> scatterGather
                                .gatherTimeout(4000L)
                );
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


