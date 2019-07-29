package com.wy.yubiao.thread.concurrentutils;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/29 10:33
 * @description: 信号量实现
 */
public class BiaoSemaphore {

    private Sync sync;

    public BiaoSemaphore(int permits){
        sync = new Sync(permits);
    }

    public void acquire(){
        sync.acquireShared(1);
    }

    public void release(){
        sync.releaseShared(1);
    }

    private class Sync extends AbstractQueuedSynchronizer{
        private int permits;

        Sync(int permits){
            this.permits = permits;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            //参数必须大于零
            if (permits <= 0)
                throw new IllegalArgumentException();
            //获取当前持有锁的线程数量
            int c = getState();
            int nextc = c+arg;
            //与信号量比较，小于信号量允许继续加锁
            if (nextc <= permits){
                if (compareAndSetState(c , nextc))
                    return 1;
            }
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int c = getState();
            if (compareAndSetState(c,c-1))
                return true;
            return false;
        }
    }
}
