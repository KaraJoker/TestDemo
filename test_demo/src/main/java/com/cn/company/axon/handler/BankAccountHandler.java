package com.cn.company.axon.handler;

import com.cn.company.axon.aggregates.BankAccountAggregate;
import com.cn.company.axon.command.CreateAccountCommand;
import com.cn.company.axon.command.WithdrawMoneyCommand;
import com.cn.company.axon.query.AccountCreatedEvent;
import com.cn.company.axon.query.MoneyWithdrawnEvent;
import com.cn.company.plan.entity.BankAccountEntry;
import com.cn.company.plan.repository.BankAccountEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Component
public class BankAccountHandler {

    @Autowired
    private BankAccountEntryRepository repository;

    @Autowired
    private Repository<BankAccountAggregate> bankAccountRepository;

    @CommandHandler
    public void handle(CreateAccountCommand command){
        log.debug("Construct a new BankAccount");
        apply(new AccountCreatedEvent(command.getAccountId(), command.getAccountName(), command.getAmount()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command){
        apply(new MoneyWithdrawnEvent(command.getAccountId(), command.getAmount()));
    }

    @EventHandler
    public void on(AccountCreatedEvent event){
        BankAccountEntry bankAccount = new BankAccountEntry(event.getAccountId(), event.getAccountName(), new BigDecimal(event.getAmount()));
        repository.save(bankAccount);
        log.info("Account {} is created with balance {}", event.getAccountName(), event.getAmount());
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) throws Exception{
        BankAccountEntry bankAccount = new BankAccountEntry(event.getAccountId(), new BigDecimal(event.getAmount()));
        BigDecimal result = bankAccount.getBalance().subtract(new BigDecimal(event.getAmount()));
        bankAccount.setBalance(result);
        if(result.compareTo(BigDecimal.ZERO)>=0)
            log.error("Cannot withdraw more money than the balance!");
        else {
            bankAccount.setAccountName("ZhangJiWei");
            repository.save(bankAccount);
            log.info("Withdraw {} from account {}, balance result: {}", event.getAccountId(), event.getAmount());
        }
    }
}
