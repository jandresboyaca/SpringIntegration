package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.sdk.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class IntegrationStream {

 /*   @Bean
    public IntegrationFlow streamFlow() {
        return IntegrationFlows.from(ConsumerTest.class, gatewayProxySpec -> gatewayProxySpec.beanName("messageConsumerStream"))
                .log(LoggingHandler.Level.WARN)
                .handle(this::getMessageGenericHandler)
                .get();
    }*/

    private Message getMessageGenericHandler(Message message, Map<String, Object> headers) {
        System.out.println(message.getTest());
        return message;
    }
}


interface ConsumerTest extends Consumer<Message> {
}