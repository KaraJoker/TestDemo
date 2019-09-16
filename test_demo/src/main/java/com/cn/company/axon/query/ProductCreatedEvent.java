package com.cn.company.axon.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@AllArgsConstructor
public class ProductCreatedEvent {

    private String id;
    private String name;
    private long price;
    private int stock;
}
