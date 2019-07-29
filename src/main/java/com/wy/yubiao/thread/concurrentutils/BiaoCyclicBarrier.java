package com.wy.yubiao.thread.concurrentutils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/29 14:46
 * @description: 栅栏实现
 */
public class BiaoCyclicBarrier {
    /**
     * 分代参数
     */
    private Object generation = new Object();

    /**
     * 通过ReentrantLock 和 Condition 搭配实现阻塞
     */
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    /**
     * 栅栏限制数量
     */
    private int parties;
    /**
     * 当前等待数量
     */
    private int count;

    public BiaoCyclicBarrier(int parties){
        if (parties <= 0)
            throw new IllegalArgumentException();
        this.parties = parties;
        this.count = 0;
    }

    private void nextGeneration(){
        generation = new Object();
        condition.signalAll();
        count = 0;
    }

    public void await(){
        this.lock.lock();
        try {
            final Object g = generation;
            int index = ++count;
            if (index >= parties){
                nextGeneration();
            }else {
                for (;;){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (g != generation){
                        return;
                    }
                }
            }

        }finally {
            this.lock.unlock();
        }
    }

}
