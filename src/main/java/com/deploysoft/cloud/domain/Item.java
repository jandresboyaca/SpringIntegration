package com.deploysoft.cloud.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private TypeFlowEnum type;
    private String message;
    private String config;

}
