package com.deploysoft.cloud.example;

import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    public GenericSelector<Integer> getIntegerGenericSelector() {
        return (Integer i) -> i > 0;
    }


    public boolean validate(Integer i) {
        return i > 0;
    }

    public String transform(Integer i) {
        return  i.toString();
    }

    public String log(String i) {
        System.out.println(i);
        return i;
    }

}
