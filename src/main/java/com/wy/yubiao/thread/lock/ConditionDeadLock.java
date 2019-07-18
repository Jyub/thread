package com.wy.yubiao.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 16:51
 * @description: Condition 死锁的情况
 */
public class ConditionDeadLock {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(()->{

           try {
                Thread.sleep(2000);
               lock.lock();

               System.out.println("获得锁");
               condition.await();
               System.out.println("i am here");
           }catch (Exception e){
               e.printStackTrace();
           }finally {
               lock.unlock();
           }
        }).start();
        lock.lock();
        condition.signal();
        lock.unlock();
    }

}
