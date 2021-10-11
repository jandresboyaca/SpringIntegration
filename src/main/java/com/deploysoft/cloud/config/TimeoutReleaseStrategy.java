package com.deploysoft.cloud.config;/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.aggregator.GroupConditionProvider;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.function.BiFunction;

@Slf4j
public class TimeoutReleaseStrategy implements ReleaseStrategy, GroupConditionProvider {

    /**
     * Default timeout is one minute.
     */
    public static final long DEFAULT_TIMEOUT = 60 * 1000;

    private final long timeout;

    public TimeoutReleaseStrategy() {
        this(DEFAULT_TIMEOUT);
    }

    /**
     * @param timeout the timeout for the release in milliseconds
     */
    public TimeoutReleaseStrategy(long timeout) {
        this.timeout = timeout;
    }

    public static final BiFunction<Message<?>, String, String> GROUP_CONDITION =
            (message, existingCondition) -> {
                MessageHeaders headers = message.getHeaders();
                if (headers.get("InitialTime") != null) {
                    Long initialTime = headers.get("InitialTime", Long.class);
                    return initialTime != null ? "" + initialTime : existingCondition;
                }
                return existingCondition;
            };

    @Override
    public boolean canRelease(MessageGroup group) {
        String condition = group.getCondition();
        long initialTime = Long.parseLong(condition);
        log.error("Initial {}, Actual {}, Result {} Timeout {} ", initialTime, System.currentTimeMillis(), System.currentTimeMillis() - initialTime, timeout);
        log.error("Evaluation time {}", System.currentTimeMillis() - initialTime > this.timeout);
        return System.currentTimeMillis() - initialTime > this.timeout;
    }

    @Override
    public BiFunction<Message<?>, String, String> getGroupConditionSupplier() {
        return GROUP_CONDITION;
    }
}
