package com.deploysoft.cloud.gateway;

import com.deploysoft.cloud.domain.Message;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway
public interface Producer {

    @Gateway(requestChannel = "queueChannel")
    String queueChannel(Integer message);
}
