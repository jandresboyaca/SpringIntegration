package com.deploysoft.integration.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public AtomicInteger integerSource() {
        return new AtomicInteger();
    }

    @Bean
    public IntegrationFlow myFlow(MessageChannel queueChannel,MessageChannel testChannel) {
        return IntegrationFlows.fromSupplier(integerSource()::getAndIncrement, c -> c.poller(Pollers.fixedRate(500)))
                .filter((Integer p) -> p > 0)
                .transform(Object::toString)
                .channel(queueChannel)
                .channel(testChannel)
                .get();
    }


   @Bean
    public IntegrationFlow myChannel() {
        return f -> f.transform(getToUpperCase())
                .channel("inputChannel")
                .log();
    }

    private GenericTransformer<String, String> getToUpperCase() {
        return (GenericTransformer<String, String>) String::toUpperCase;
    }


    /*@Bean
    public IntegrationFlow test() {
        return IntegrationFlows.from("myChannel")
                .transform((GenericTransformer<String, String>) String::toUpperCase)
                .log().get();
    }*/

/*    @ServiceActivator(inputChannel = "output")
       public void consume(String test) {
           System.out.println(test);
       }*/

    @Bean
    public MessageChannel queueChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel testChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "queueChannel")
    public void consume2(String test) {
        System.out.println(test);
    }

    @ServiceActivator(inputChannel = "testChannel")
    public void consume1(String test) {
        System.out.println(test);
    }

}
