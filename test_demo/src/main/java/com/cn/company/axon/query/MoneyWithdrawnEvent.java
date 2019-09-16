package com.cn.company.axon.query;


import com.cn.company.axon.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@AllArgsConstructor
public class MoneyWithdrawnEvent {
    private AccountId accountId;
    private long amount;
}
