package com.cn.company.msg.receiver;

import com.cn.company.msg.channel.sink.GreetingsStreamSink;
import com.cn.company.msg.msjobj.Greetings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@EnableBinding(GreetingsStreamSink.class)
public class GreetingsListener {

    @StreamListener(GreetingsStreamSink.INPUT)
    public void handleGreetings(@Payload Greetings greetings) {
        log.info("Received greetings: {}", greetings);
    }
}

