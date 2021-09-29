package com.deploysoft.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.BaseIntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;

import java.util.Arrays;
import java.util.concurrent.Executors;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public IntegrationFlow convert() {
        return f -> f
                .handle(String.class, (message, headers) -> new StringBuilder(message).reverse().toString())
                .transform(s -> s.toString().toUpperCase());
    }

    @Bean
    public IntegrationFlow parallelSplitRouteAggregateFlow() {
        return f -> f
                .handle((p, h) -> Arrays.asList(1, 2, 3))
                .split()
                .channel(MessageChannels.executor(Executors.newCachedThreadPool()))
                .<Integer, Boolean>route(o -> o % 2 == 0, m -> m
                        .subFlowMapping(true, oddFlow())
                        .subFlowMapping(false, evenFlow()));
    }

    @Bean
    public IntegrationFlow oddFlow() {
        return flow -> flow.<Integer>handle((payload, headers) -> "odd")
                .channel("agg.input");
    }

    @Bean
    public IntegrationFlow evenFlow() {
        return flow -> flow.<Integer>handle((payload, headers) -> "even")
                .channel("agg.input");
    }

    @Bean
    public IntegrationFlow agg() {
        return BaseIntegrationFlowDefinition::aggregate;
    }

}


