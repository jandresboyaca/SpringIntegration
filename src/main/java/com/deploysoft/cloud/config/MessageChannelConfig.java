package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MessageChannelConfig {

    @Bean
    public MessageChannel testChannel() {
        return MessageChannels.queue().get();
    }

}
