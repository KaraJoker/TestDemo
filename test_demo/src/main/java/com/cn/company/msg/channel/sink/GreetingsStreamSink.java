package com.cn.company.msg.channel.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GreetingsStreamSink {

    String INPUT = "greetings-in";

    @Input(INPUT)
    SubscribableChannel inboundGreetings();
}
