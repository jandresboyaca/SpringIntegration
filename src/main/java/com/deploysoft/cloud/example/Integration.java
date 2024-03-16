package com.deploysoft.cloud.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
public class Integration {


    @Bean
    DirectChannel inputChannel() {
        return new DirectChannel();
    }


    @Bean
    public IntegrationFlow initialFlow(ServiceA serviceA) {
        return IntegrationFlows.from(inputChannel())
                .filter(serviceA::validate)
                .transform(serviceA::transform)
                .handle(String.class, (p, m) -> serviceA.log(p))
                .get();
    }

    private static GenericSelector<Integer> getIntegerGenericSelector() {
        return (Integer i) -> i > 0;
    }


}
