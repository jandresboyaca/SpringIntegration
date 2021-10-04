package com.deploysoft.cloud.service;

import com.deploysoft.cloud.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LogicService {


    public Message callFakeService(Message value, MessageHeaders headers) {
        log.info("[{}]", headers);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

}
