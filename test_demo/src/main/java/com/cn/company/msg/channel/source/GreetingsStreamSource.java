package com.cn.company.msg.channel.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GreetingsStreamSource {

    String OUTPUT = "greetings-out";

    @Output(OUTPUT)
    MessageChannel outboundGreetings();
}
