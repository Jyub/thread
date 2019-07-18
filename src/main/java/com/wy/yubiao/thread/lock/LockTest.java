package com.wy.yubiao.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 13:45
 * @description: Lock API 使用
 */
public class LockTest {

    static  Lock lock = new ReentrantLock(false);

    public static void main(String[] args) throws InterruptedException {

        lock.lock();

        Thread thread = new Thread(() -> {
            //尝试获取锁，不阻塞
//            boolean b = lock.tryLock();
//            System.out.println("获取锁状态" + b);

            //带有超时时间获取
//            try {
//                boolean b = lock.tryLock(10, TimeUnit.SECONDS);
//                System.out.println("获取锁状态" + b);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //可以中断的获取锁
            try {
                System.out.println("i am running");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("i am interrupted");
            }
        });

        thread.start();
        Thread.sleep(6000);
        //lock.unlock();
        thread.interrupt();
    }
}
