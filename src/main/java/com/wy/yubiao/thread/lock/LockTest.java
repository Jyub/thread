package com.wy.yubiao.thread.lock;

import com.wy.yubiao.thread.lock.demo.JamesReentrantLock;

import java.util.concurrent.locks.Lock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 13:45
 * @description: Lock API 使用
 */
public class LockTest {

    static  Lock lock = new JamesReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        lock.lock();

        Thread thread = new Thread(() -> {
            //尝试获取锁，不阻塞
            boolean b = lock.tryLock();
            System.out.println("获取锁状态" + b);
            System.out.println("进入线程"+Thread.currentThread().getName());
            lock.lock();
            System.out.println("获取到锁");
            lock.unlock();
            //带有超时时间获取
//            try {
//                boolean b = lock.tryLock(10, TimeUnit.SECONDS);
//                System.out.println("获取锁状态" + b);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //可以中断的获取锁
            /*try {
                System.out.println("i am running");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("i am interrupted");
            }*/
        });
        Thread thread1 = new Thread(()->{
            System.out.println("进入线程"+Thread.currentThread().getName());
            lock.lock();
            try {
                System.out.println("正在执行"+Thread.currentThread().getName());
            }finally {
                lock.unlock();
            }
        });
        thread.start();
        thread1.start();
        Thread.sleep(5000);
        lock.unlock();
        //thread.interrupt();
    }
}
