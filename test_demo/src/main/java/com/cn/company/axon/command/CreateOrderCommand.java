package com.cn.company.axon.command;

import com.cn.company.axon.model.OrderId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@AllArgsConstructor
public class CreateOrderCommand {

    private OrderId orderId;
    private String username;
    private Map<String, Integer> products;

    public CreateOrderCommand(String username, Map<String, Integer> products) {
        this.orderId = new OrderId();
        this.username = username;
        this.products = products;
    }
}
