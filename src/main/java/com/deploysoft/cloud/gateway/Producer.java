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
public interface Producer {

    @Gateway(requestChannel = "testChannel")
    List<String> queueChannel(Item message, @Headers Map<String, Object> map);

    @Gateway(requestChannel = "splitterChannel")
    List<String> splitterChannel(Order message, @Headers Map<String, Object> map);


    @Gateway(requestChannel = "testChannelExecutor")
    void executorTest(Order message);
}
