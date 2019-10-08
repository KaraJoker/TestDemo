package com.highto.framework.notify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tan on 2017/7/27.
 */
public abstract class AbstractNotifier implements Notifier {
    //当前Notifier发送了多少条
    private AtomicInteger sequence = new AtomicInteger(0);

    @Override
    public void notify(String msg) {
        doNotify(msg.getBytes());
    }

    @Override
    public void notify(byte[] msg) {
        doNotify(msg);
    }

    public abstract void doNotify(byte[] msg);

    public int getAndIncrement() {
        return sequence.getAndIncrement();
    }
}
