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
    public  void increament(){
        lock.lock();
        lock.lock();
        try {
            i++;
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception{
        CounterLock counterLock = new CounterLock();
        for (int i=0; i< 10; i++) {
            new Thread(()->{
               for (int j=0; j<10000;j++){
                   counterLock.increament();
               }
            }).start();
        }
        Thread.sleep(3000L);
        System.out.println(counterLock.i);
    }
}
