package com.wy.yubiao.thread.concurrentutils;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/25 9:27
 * @description: 自定义计数器
 */
public class BiaoCountDownLatch {
    private Sync sync;

    public BiaoCountDownLatch(int count){
        sync = new Sync(count);
    }

    public void countDown(){
        sync.releaseShared(1);
    }

    public void await(){
        sync.acquireShared(1);
    }

    class Sync extends AbstractQueuedSynchronizer{
        Sync(int state){
            setState(state);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 0) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;){
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c-1;
                if (compareAndSetState(c,nextc))
                    return nextc==0;
            }
        }
    }
}
