package com.deploysoft.cloud.service;

import com.deploysoft.cloud.domain.MessageDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LogicService {


    public MessageDomain callFakeServiceTimeout5(MessageDomain value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    public MessageDomain callFakeServiceTimeout20(MessageDomain value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MessageDomain("new message", "test");
    }

    public MessageDomain callFakeServiceTimeout15(MessageDomain value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MessageDomain("new message", "test");
    }

}
