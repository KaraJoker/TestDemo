package com.cn.company.axon.aggregates;

import com.cn.company.axon.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import java.math.BigDecimal;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountAggregate {

    private static final Logger LOGGER = getLogger(BankAccountAggregate.class);

    @AggregateIdentifier
    private AccountId accountId;
    private String accountName;
    private BigDecimal balance;
}
