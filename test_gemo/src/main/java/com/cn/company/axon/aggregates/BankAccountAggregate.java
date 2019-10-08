package com.cn.company.axon.aggregates;

import com.cn.company.axon.model.AccountId;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import java.math.BigDecimal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Aggregate
public class BankAccountAggregate {

    private static final Logger LOGGER = getLogger(BankAccountAggregate.class);

    @AggregateIdentifier
    private AccountId accountId;
    private String accountName;
    private BigDecimal balance;

    public BankAccountAggregate(){}

    public BankAccountAggregate(AccountId accountId, String accountName, BigDecimal balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
