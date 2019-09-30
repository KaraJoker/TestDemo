package com.cn.company.plan.entity;

import com.cn.company.axon.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountEntry {

    private static final Logger LOGGER = getLogger(BankAccountEntry.class);

    @Id
    private String accountId;
    @Column
    private String accountName;
    @Column
    private BigDecimal balance;

    public BankAccountEntry(AccountId accountId, BigDecimal balance){

    }
}
