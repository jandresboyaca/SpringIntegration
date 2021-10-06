package com.deploysoft.cloud.service;

import com.deploysoft.cloud.domain.MessageDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LogicService {


    public MessageDomain callFakeServiceTimeout2(MessageDomain value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    public MessageDomain callFakeServiceTimeout4(MessageDomain value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MessageDomain("new message", "test");
    }

}
