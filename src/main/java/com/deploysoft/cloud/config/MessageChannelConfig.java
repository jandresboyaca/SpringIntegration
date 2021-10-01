package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executors;

@Configuration
public class MessageChannelConfig {

    @Bean
    public MessageChannel queueChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public MessageChannel publishSubscribe() {
        return MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
    }

    //TODO
    @Bean
    public MessageChannel flux() {
        return MessageChannels.flux().get();
    }

}
