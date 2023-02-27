package com.deploysoft.cloud.flows;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.domain.TypeFlowEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;

@Slf4j
@Configuration
public class RouterExample {

    @Bean
    public IntegrationFlow routerTest(IntegrationFlow subFlowA, IntegrationFlow subFlowB) {
        return flow -> flow
                .routeToRecipients(router -> router
                        .recipientFlow(this::checkA, subFlowA)
                        .recipientFlow(this::checkB, subFlowB)
                        .defaultOutputToParentFlow())
                .channel("resultOfRouting")
                .transform(e -> e);
    }

    private boolean checkA(Item item) {
        return item.getType().equals(TypeFlowEnum.A);
    }

    private boolean checkB(Item item) {
        return item.getType().equals(TypeFlowEnum.B);
    }

    @Bean
    public IntegrationFlow subFlowA() {
        return f -> f.log(LoggingHandler.Level.WARN).handle(Item.class, (m, p) -> {
            m.setMessage("subFlow1");
            return m;
        }).channel("resultOfRouting");
    }

    @Bean
    public IntegrationFlow subFlowB() {
        return f -> f.handle(Item.class, (m, p) -> {
            m.setMessage("subFlow2");
            try {
                throw new RuntimeException("test");
            } catch (Exception e) {
                m.setMessage("subFlow2WithExeception");
            }
            return m;
        }).channel("resultOfRouting");
    }
}
