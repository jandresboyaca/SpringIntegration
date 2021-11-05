package com.deploysoft.cloud.service;

import com.deploysoft.cloud.domain.Item;
import com.deploysoft.cloud.domain.TypeFlowEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LogicService {


    public String callFakeServiceTimeout5(Item value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return TypeFlowEnum.A.name();
    }

    public String callFakeServiceTimeout10(Item value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(10);
            //throw new RuntimeException("test");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return TypeFlowEnum.B.name();
    }

    public String callFakeServiceTimeout20(Item value, MessageHeaders headers) {
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return TypeFlowEnum.C.name();
    }

}
