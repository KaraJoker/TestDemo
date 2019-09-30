package com.cn.company.axon.query;


import com.cn.company.axon.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by Edison Xu on 2017/3/7.
 */
public class MoneyWithdrawnEvent {

    @TargetAggregateIdentifier
    private AccountId accountId;
    private long amount;

    public MoneyWithdrawnEvent(AccountId accountId, long amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public long getAmount() {
        return amount;
    }
}
