package com.cn.company.axon.aggregates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@Aggregate
@AllArgsConstructor
public class ProductAggregate {

    private static final Logger LOGGER = getLogger(ProductAggregate.class);

    @AggregateIdentifier
    private String id;
    private String name;
    private int stock;
    private long price;
}
