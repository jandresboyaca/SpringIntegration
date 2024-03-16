package com.deploysoft.cloud.example;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway
public interface EIPGateway {

    @Gateway(requestChannel = "inputChannel")
    String testFlow(Integer message);

}
