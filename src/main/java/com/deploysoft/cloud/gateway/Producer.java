package com.deploysoft.cloud.gateway;

import com.deploysoft.cloud.domain.MessageDomain;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@MessagingGateway
public interface Producer {

    @Gateway(requestChannel = "queueFlow.input")
    List<MessageDomain> queueChannel(MessageDomain message, @Headers Map<String, Object> map);
}
