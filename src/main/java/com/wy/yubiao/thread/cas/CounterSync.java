package com.wy.yubiao.thread.cas;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 10:41
 * @description: 线程不安全的计数器
 */
public class CounterSync {
    public volatile int i;

    public synchronized void increament(){
        i++;
    }
}
