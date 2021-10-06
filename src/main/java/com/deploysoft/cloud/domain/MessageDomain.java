package com.deploysoft.cloud.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageDomain {

    private String message;
    private String config;
}