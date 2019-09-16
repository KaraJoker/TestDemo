package com.cn.company.controller;

import com.cn.company.axon.command.CreateAccountCommand;
import com.cn.company.axon.command.WithdrawMoneyCommand;
import com.cn.company.axon.model.AccountId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/admin")
public class NotifyController {

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(value = "/replace", method = RequestMethod.GET)
    public void replace(HttpServletRequest request) {
        AccountId id = new AccountId();
        commandGateway.send(new CreateAccountCommand(id, "JiWei",500));
        commandGateway.send(new WithdrawMoneyCommand(id,1500));
    }
}
