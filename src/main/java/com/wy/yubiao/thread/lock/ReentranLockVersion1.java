package com.wy.yubiao.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 17:33
 * @description: 自定义实现ReentranLock
 */
public class ReentranLockVersion1 implements Lock{
    //存储当前占用锁的线程
    private AtomicReference<Thread> owner = new AtomicReference<>();
    //重入锁计数器
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void lock() {
        //判断count是否等于0，如果为空，尝试持有锁


    }

    @Override
    public boolean tryLock() {
        int ct = count.get();
        if (ct == 0){

        }
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }



    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
