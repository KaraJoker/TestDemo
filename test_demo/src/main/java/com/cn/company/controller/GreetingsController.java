package com.cn.company.controller;

import com.cn.company.msg.msjobj.Greetings;
import com.cn.company.msg.service.GreetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @Autowired
    GreetingsService greetingsService;

    @GetMapping("/greetings")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void greetings(@RequestParam("message") String message) {
        Greetings greetings = Greetings.builder()
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
        greetingsService.sendGreeting(greetings);
    }
}

