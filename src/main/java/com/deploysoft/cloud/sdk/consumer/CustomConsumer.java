package com.deploysoft.cloud.sdk.consumer;

import com.deploysoft.cloud.exception.CustomException;
import com.deploysoft.cloud.repository.TestRepository;
import com.deploysoft.cloud.repository.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomConsumer implements Consumer<String> {

    private final TestRepository repository;

    @Override
    public void accept(String s) {
        Optional<Item> byId = repository.findById(s);
        log.info("Consuming [{}]...", s);
        if (byId.isEmpty()) {
            throw new CustomException("not yet, retry it...");
        } else {
            throw new IllegalArgumentException("not retry");
        }

    }
}
