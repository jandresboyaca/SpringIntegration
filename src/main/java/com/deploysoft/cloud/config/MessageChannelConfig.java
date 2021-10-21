package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class MessageChannelConfig {

    @Bean
    public MessageChannel directChannel() {
        return new DirectChannel();
    }

}
