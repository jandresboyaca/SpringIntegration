package com.deploysoft.cloud.gateway;

import com.deploysoft.cloud.domain.MessageDomain;
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
    List<MessageDomain> queueChannel(MessageDomain message, @Headers Map<String, Object> map);
}
