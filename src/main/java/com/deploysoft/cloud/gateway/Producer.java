package com.deploysoft.cloud.gateway;

import com.deploysoft.cloud.domain.Message;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway
public interface Producer {

    @Gateway(requestChannel = "convert.input")
    String produceAndConsume(String message);

    @Gateway(requestChannel = "queueChannel")
    String queueChannel(Message message);
}
