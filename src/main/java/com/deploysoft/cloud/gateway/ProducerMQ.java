package com.deploysoft.cloud.gateway;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProducerMQ {

    private final StreamBridge   streamBridge;

    public  void publish() {
        streamBridge.send("destChannel","test");
    }
}
