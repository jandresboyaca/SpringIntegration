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
    public IntegrationFlow routerTest(IntegrationFlow subFlow1, IntegrationFlow subFlow2) {
        return flow -> flow.routeToRecipients(router -> router
                        .recipientFlow(this::check, subFlow1)
                        .recipientFlow(this::check, subFlow2)
                        .defaultOutputToParentFlow())
                .transform(e -> e);
    }

    private boolean check(Item item) {
        return item.getType().equals(TypeFlowEnum.A);
    }

    @Bean
    public IntegrationFlow subFlow1() {
        return f -> f.log(LoggingHandler.Level.WARN).handle(Item.class, (m, p) -> {
            m.setMessage("subFlow1");
            return m;
        });
    }

    @Bean
    public IntegrationFlow subFlow2() {
        return f -> f.handle(Item.class, (m, p) -> {
            m.setMessage("subFlow2");
            try {
                throw new RuntimeException("test");
            } catch (Exception e) {
                m.setMessage("subFlow2WithExeception");
            }
            return m;
        });
    }
}
