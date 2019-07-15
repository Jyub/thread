package com.wy.yubiao.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 10:41
 * @description: 线程不安全的计数器
 */
public class CounterAtomic {
    AtomicInteger i = new AtomicInteger(0);

    public void increament(){
        i.getAndIncrement();
    }
}
