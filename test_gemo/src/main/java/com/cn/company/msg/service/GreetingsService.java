package com.cn.company.msg.service;

import com.cn.company.msg.channel.source.GreetingsStreamSource;
import com.cn.company.msg.msjobj.Greetings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Slf4j
@Service
@EnableBinding(GreetingsStreamSource.class)
public class GreetingsService {

    @Autowired
    GreetingsStreamSource greetingsStreamSource;

    public void sendGreeting(final Greetings greetings) {
        log.info("Sending greetings {}", greetings);
        MessageChannel messageChannel = greetingsStreamSource.outboundGreetings();
        messageChannel.send(MessageBuilder
                .withPayload(greetings)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build()
        );
    }
}

