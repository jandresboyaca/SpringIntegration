package com.deploysoft.cloud.service;

import com.deploysoft.cloud.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class TransformerService {


    public Message transform(Message message) {
        message.setMessage(message.getMessage().toUpperCase());
        return message;
    }

}
