package com.cn.company.axon.query;

import com.cn.company.axon.model.OrderId;
import com.cn.company.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Map;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@AllArgsConstructor
public class OrderCreatedEvent {

    @TargetAggregateIdentifier
    private OrderId orderId;
    private String username;
    private Map<String, OrderProduct> products;
}
