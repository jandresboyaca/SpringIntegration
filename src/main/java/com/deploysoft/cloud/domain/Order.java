package com.deploysoft.cloud.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Item> items;

}
