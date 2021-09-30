package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MessageConfig {

    @Bean
    public MessageChannel queueChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public MessageChannel publishSubscribe() {
        return MessageChannels.publishSubscribe().get();
    }

    //TODO
    @Bean
    public MessageChannel flux() {
        return MessageChannels.flux().get();
    }

}
