package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.Message;
import com.deploysoft.cloud.service.TransformerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@Configuration
public class QueueIntegration {

    @Bean
    public IntegrationFlow convert() {
        return f -> f
                .handle(String.class, (message, headers) -> new StringBuilder(message).reverse().toString())
                .transform(s -> s.toString().toUpperCase());
    }

    @Bean
    public IntegrationFlow aFlow() {
        return flow -> flow.<Message>handle((payload, headers) -> "correct value");
    }

    @Bean
    public IntegrationFlow bFlow() {
        return flow -> flow.<Message>handle((payload, headers) -> "invalid value");
    }

    @Bean
    public IntegrationFlow agg() {
        return BaseIntegrationFlowDefinition::aggregate;
        //return f -> f.aggregate();
    }


    @Bean
    public IntegrationFlow queueFlow(MessageChannel queueChannel, TransformerService service) {
        return IntegrationFlows.from(queueChannel)
                .transform(service::transform)
                .split()
                //.channel(MessageChannels.executor(Executors.newCachedThreadPool())) handle with multi thread
                .<Message, Boolean>route(message -> message.getMessage().equals("MESSAGE"),
                        r -> r
                                .subFlowMapping(true, aFlow())
                                .subFlowMapping(false, bFlow())
                ).get();

    }

}


