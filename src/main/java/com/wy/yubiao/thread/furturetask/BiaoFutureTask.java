package com.wy.yubiao.thread.furturetask;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/29 15:22
 * @description: 仿写FutureTask
 */
public class BiaoFutureTask<T> implements Runnable {

    /**
     * 存储Callable对象
     */
    private Callable callable;
    private int state = NEW;
    private static final int NEW = 1;
    private static final int RUNNING = 2;
    private static final int FINISHED = 3;
    /**
     * 存储执行结果
     */
    private T result;

    /**
     * 存储要执行的线程
     */
    private AtomicReference<Thread> runner = new AtomicReference<>();
    /**
     * 存储等待获取结果的线程
     */
    private LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public BiaoFutureTask(Callable callable){
        this.callable = callable;
    }
    @Override
    public void run() {
        if (state != NEW)
            return;
        if (runner.compareAndSet(null,Thread.currentThread())){
            try {
                state = RUNNING;
                result = (T)callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                state = FINISHED;
            }

            //执行结束，唤醒等待的线程
            while (true){
                Thread waiter = waiters.poll();
                if (waiter == null)
                    break;
                LockSupport.unpark(waiter);

            }
        }
    }

    public T get(){
        if (state != FINISHED)
            waiters.offer(Thread.currentThread());
        while (state != FINISHED)
            LockSupport.park();
        return result;
    }
}
