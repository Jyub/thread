package com.wy.yubiao.thread.cas;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 10:41
 * @description: 线程不安全的计数器
 */
public class CounterLock {
    public volatile int i;
    Lock lock = new ReentrantLock();
    public synchronized void increament(){
        lock.lock();
        try {
            i++;
        }finally {
            lock.unlock();
        }
    }
}
