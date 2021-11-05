package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.TimeUnit;

@Configuration
public class MessageChannelConfig {

    @Bean
    public MessageChannel testChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel splitterChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        var periodicTrigger = new PeriodicTrigger(500L, TimeUnit.MILLISECONDS);
        var pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(periodicTrigger);
        pollerMetadata.setMaxMessagesPerPoll(1);
        return pollerMetadata;
    }
}
