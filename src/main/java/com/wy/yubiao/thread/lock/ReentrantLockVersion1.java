package com.wy.yubiao.thread.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 17:33
 * @description: 自定义实现ReentranLock
 */
public class ReentrantLockVersion1 implements Lock{
    //存储当前占用锁的线程
    private AtomicReference<Thread> owner = new AtomicReference<>();
    //重入锁计数器
    private AtomicInteger count = new AtomicInteger(0);

    private LinkedBlockingQueue<Thread> waiter = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        if (!tryLock()){
            //将当前线程放入阻塞队列
            waiter.offer(Thread.currentThread());
            for (;;){
                Thread peek = waiter.peek();
                //取出头部线程，判断是否为当前线程
                if (peek == Thread.currentThread()){
                    if(tryLock()){
                        //成功，剔除队列
                        waiter.poll();
                        return;
                    }else {
                        //失败，挂起等待
                        LockSupport.park();
                    }
                }else {
                    //如果不是,挂起等待
                    LockSupport.park();
                }
            }
        }
    }

    @Override
    public boolean tryLock() {
        int ct = count.get();
        //判断count是否等于0，如果为空，尝试持有锁
        if (ct == 0){
            //CAS交换，如果交换成功获取到锁，将owner置为当前线程
            if (count.compareAndSet(0,1)){
                owner.set(Thread.currentThread());
                return true;
            }
        }else if (ct > 0){
            //大于零，判断线程是否为当前线程，如果是锁计数器累加
            if (owner.get() == Thread.currentThread()){
                count.set(ct+1);
                return true;
            }
        }else {
            throw new IllegalMonitorStateException();
        }
        return false;
    }

    @Override
    public void unlock() {
        if (tryUnlock()){
            Thread th = waiter.peek();
            if (th != null){
                LockSupport.unpark(th);
            }
        }
    }

    public boolean tryUnlock(){
        if (owner.get() != Thread.currentThread())
            throw new IllegalMonitorStateException();
        int i = count.get();
        i = i -1;
        count.set(i);

        if (i == 0){
            //锁计数器归零，释放锁
            owner.compareAndSet(Thread.currentThread(),null);
            return  true;
        }
        return false;
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
