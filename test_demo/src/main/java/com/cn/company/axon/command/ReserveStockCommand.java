package com.cn.company.axon.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by Edison Xu on 2017/3/9.
 */
@Getter
@AllArgsConstructor
public class ReserveStockCommand {

    @TargetAggregateIdentifier
    private String productId;
    private long number;
}
