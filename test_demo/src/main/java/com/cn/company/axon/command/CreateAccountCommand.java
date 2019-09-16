package com.cn.company.axon.command;

import com.cn.company.axon.model.AccountId;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Edison Xu on 2017/3/7.
 */
@Getter
@AllArgsConstructor
public class CreateAccountCommand {

    private AccountId accountId;
    private String accountName;
    private long amount;
}
