package com.deploysoft.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface Action {

    @Gateway(requestChannel = "myChannel.input")
    void sendToMyChannel(String test);
}
