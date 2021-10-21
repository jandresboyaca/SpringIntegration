package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.config.TimeoutReleaseStrategy;
import com.deploysoft.cloud.domain.MessageDomain;
import com.deploysoft.cloud.service.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.store.MessageGroupStoreReaper;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class QueueIntegration {

    @Bean
    public ExecutorService executorService() {
        //FIXME check Executors.newFixedThreadPool()s
        return Executors.newCachedThreadPool();
    }

    @Bean
    public IntegrationFlow aFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
                .handle(service::callFakeServiceTimeout5)
                .transform((MessageDomain.class), message -> {
                    message.setMessage(new StringBuilder(message.getMessage()).reverse().toString());
                    return message;
                }).get();
    }

    @Bean
    public IntegrationFlow bFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
                .handle(service::callFakeServiceTimeout10)
                .transform((MessageDomain.class), message -> {
                    message.setMessage(message.getMessage().toUpperCase());
                    return message;
                }).get();
    }

    @Bean
    public IntegrationFlow cFlow(LogicService service) {
        return IntegrationFlows.from(MessageChannels.executor(executorService()))
                .handle(service::callFakeServiceTimeout20)
                .transform((MessageDomain.class), message -> {
                    message.setMessage(message.getMessage().concat("test"));
                    return message;
                }).get();
    }

    @Bean
    public IntegrationFlow queueFlow(MessageChannel testChannel, LogicService service) {
        return IntegrationFlows.from(testChannel)
                .scatterGather(scatterer -> scatterer
                                .applySequence(true)
                                .recipientFlow(aFlow(service))
                                .recipientFlow(bFlow(service))
                                .recipientFlow(cFlow(service))
                        , aggregatorSpec ->
                                aggregatorSpec
                                        .releaseStrategy(new TimeoutReleaseStrategy(13500L)) //Release strategy that release that has less than 13.5 sec since the group was created
                                        .groupTimeout(500L) //Wait 500 milliseconds for other message , call again the release strategy but will be false again because is in the time
                                        .sendPartialResultOnExpiry(true) // Release messages although they are not complete
                        //TODO Compesite Message , Erro Handling
                ).get();
    }

    @Bean
    public MessageGroupStoreReaper reaper(SimpleMessageStore messageStore) {
        MessageGroupStoreReaper messageGroupStoreReaper = new MessageGroupStoreReaper();
        messageGroupStoreReaper.setTimeout(TimeUnit.SECONDS.toMillis(15));
        messageGroupStoreReaper.setMessageGroupStore(messageStore);
        return messageGroupStoreReaper;
    }

    @Bean
    public SimpleMessageStore messageStore() {
        return new SimpleMessageStore();
    }

    @Bean
    public TaskScheduler taskSchedulerTest(MessageGroupStoreReaper reaper) {
        ConcurrentTaskScheduler threadPoolTaskScheduler = new ConcurrentTaskScheduler();
        threadPoolTaskScheduler.schedule(reaper, new PeriodicTrigger(1, TimeUnit.SECONDS));
        return threadPoolTaskScheduler;
    }

}


