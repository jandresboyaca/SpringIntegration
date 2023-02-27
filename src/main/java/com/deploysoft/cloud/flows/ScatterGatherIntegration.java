package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.config.TimeoutReleaseStrategy;
import com.deploysoft.cloud.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@Configuration
public class ScatterGatherIntegration {

    @Bean
    public IntegrationFlow queueFlow(MessageChannel testChannel, IntegrationFlow aFlow, IntegrationFlow bFlow, IntegrationFlow cFlow) {
        return IntegrationFlows.from(testChannel)
                .scatterGather(scatterer -> scatterer
                                .applySequence(true)
                                .recipientFlow(aFlow)
                                .recipientFlow(bFlow)
                                .recipientFlow(cFlow)
                        , aggregatorSpec ->
                                aggregatorSpec
                                        .releaseStrategy(new TimeoutReleaseStrategy(13500L)) //Release strategy that release that has less than 13.5 sec since the group was created
                                        .groupTimeout(500L) //Wait 500 milliseconds for other message , call again the release strategy but will be false again because is in the time
                                        .sendPartialResultOnExpiry(true)// Release messages although they are not complete
                ).get();
    }

    @ServiceActivator(inputChannel = "nullChannel")
    public Message<?> processAsyncScatterError(MessagingException payload) {
        return MessageBuilder.withPayload(payload.getCause().getCause())
                .copyHeaders(payload.getFailedMessage().getHeaders())
                .build();
    }

    @ServiceActivator(inputChannel = "errorChannel")
    public Message<?> processAsyncScatterError2(MessagingException payload) {
        return MessageBuilder.withPayload(payload.getCause().getCause())
                .copyHeaders(payload.getFailedMessage().getHeaders())
                .build();
    }
    /*@Bean
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
    */
}


