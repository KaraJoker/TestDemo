package com.cn.company.axon.query;

import com.cn.company.axon.model.AccountId;
import com.cn.company.axon.model.OrderId;
import com.cn.company.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Map;

/**
 * Created by Edison Xu on 2017/3/7.
 */
public class AccountCreatedEvent {

    @TargetAggregateIdentifier
    private AccountId accountId;
    private String accountName;
    private long amount;

    public AccountCreatedEvent(AccountId accountId, String accountName, long amount) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.amount = amount;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getAmount() {
        return amount;
    }
}
