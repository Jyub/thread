package com.wy.yubiao.thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/18 16:42
 * @description: Lock实现阻塞队列
 */
public class BlockByLock {

    private List queue = new ArrayList<>();
    private int length;
    private Lock lock = new ReentrantLock();
    private Condition putCondition = lock.newCondition();
    private Condition takeCondition = lock.newCondition();

    public BlockByLock(int length){
        this.length = length;
    }

    public void put(Object obj){
        lock.lock();
        try {
            if (queue.size() < length){
                queue.add(obj);
                System.out.println("put:"+obj);
                takeCondition.signal();
            }else {
                System.out.println("队列已满，等待");
                putCondition.await();
                queue.add(obj);
                System.out.println("put:"+obj);
                takeCondition.signal();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Object take(){
        Object obj = null;
        lock.lock();
        try {
            if (queue.size() > 0){
                obj = queue.get(0);
                queue.remove(0);
                System.err.println("take:"+obj);
                putCondition.signal();
            }else {
                System.out.println("队列为零，等待");
                takeCondition.await();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {
        BlockByLock bb = new BlockByLock(5);

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    bb.put("x" + i);
                }
            }
        }.start();

        Thread.sleep(1000L);
        System.out.println("开始从队列中取元素...");
        for (int i = 0; i < 9; i++) {
            bb.take();
            Thread.sleep(10);
        }
        Thread.sleep(100);
        System.out.println(bb.queue.size());
    }
}
