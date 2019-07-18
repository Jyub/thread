package com.wy.yubiao.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 14:42
 * @description: Condition 使用
 */
public class ConditionDemo {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException{

        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            try {
                lock.lock();
                Thread.sleep(2000L);
                condition.await();
                System.out.println("i am here");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
            }finally {
                lock.unlock();
            }

        });
        t1.start();
        t2.start();
        Thread.sleep(1000);
        System.out.println(t1.getState());
        System.out.println(t2.getState());
        Thread.sleep(3000);
        System.out.println(t2.getState());
        lock.lock();
        condition.signal();
        lock.unlock();
    }

}
