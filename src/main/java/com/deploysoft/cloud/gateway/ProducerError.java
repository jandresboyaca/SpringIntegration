package com.deploysoft.cloud.gateway;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.domain.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@MessagingGateway
public interface ProducerError {

    @Gateway(requestChannel = "testChannelExecutor")
    void testProducer(Item message);

}
