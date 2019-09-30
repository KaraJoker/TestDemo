package com.cn.company.axon.command;

import com.cn.company.axon.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@AllArgsConstructor
public class WithdrawMoneyCommand {

    @TargetAggregateIdentifier
    private AccountId accountId;
    private long amount;
}
