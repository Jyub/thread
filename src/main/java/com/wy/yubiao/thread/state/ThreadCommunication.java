package com.wy.yubiao.thread.state;

import java.util.concurrent.locks.LockSupport;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/1 15:52
 * @description: 线程通信
 */
public class ThreadCommunication {

    static ThreadCommunication baozi = null;

    static void suspendResume() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (baozi == null) {
                System.out.println("包子铺没有包子，挂起");
                Thread.currentThread().suspend();
            }
            System.out.println("买到包子了");
        });
        thread.start();
        Thread.sleep(1000);
        baozi = new ThreadCommunication();
        System.out.println("生产出包子了");
        Thread.sleep(100);
        thread.resume();
    }

    static void suspendResumeDeadLock() throws InterruptedException {
        Object obj = new Object();
        Thread thread = new Thread(() -> {
            while (baozi == null) {
                System.out.println("包子铺没有包子，挂起");
                synchronized (obj) {
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("买到包子了");

        });
        thread.start();
        Thread.sleep(1000);
        synchronized (obj) {
            baozi = new ThreadCommunication();
        }
        thread.resume();
        System.out.println("生产出包子了");
        Thread.sleep(100);

    }

    static void suspendResumeDeadLock1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (baozi == null) {
                System.out.println("包子铺没有包子，挂起");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.currentThread().suspend();
            }
            System.out.println("买到包子了");
        });
        thread.start();
        Thread.sleep(1000);
        baozi = new ThreadCommunication();
        System.out.println("生产出包子了");
        Thread.sleep(100);
        thread.resume();
    }

    static void waitNotify() throws InterruptedException {
        new Thread(()->{
           while (baozi == null){
               System.out.println("没有包子，进入等待");
               synchronized (ThreadCommunication.class){
                   try {
                       ThreadCommunication.class.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
            System.out.println("买到包子了" );
        }).start();
        Thread.sleep(2000);
        baozi = new ThreadCommunication();
        synchronized (ThreadCommunication.class){
            ThreadCommunication.class.notifyAll();
        }
        System.out.println("生产出包子了");
    }

    static void waitNotifyDeadLock() throws InterruptedException {
        new Thread(()->{
            while (baozi == null){
                System.out.println("没有包子，进入等待");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (ThreadCommunication.class){
                    try {
                        ThreadCommunication.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("买到包子了" );
        }).start();
        Thread.sleep(1000);
        baozi = new ThreadCommunication();
        synchronized (ThreadCommunication.class){
            ThreadCommunication.class.notifyAll();
        }
        System.out.println("生产出包子了");
    }

    static void parkUnpark() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (baozi == null) {
                System.out.println("包子铺没有包子，进入等待");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
            }
            System.out.println("买到包子了");
        });
        thread.start();
        Thread.sleep(100);
        baozi = new ThreadCommunication();
        LockSupport.unpark(thread);
        System.out.println("生产出包子了");
    }

    static void parkUnparkDeadLock() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (baozi == null) {
                System.out.println("包子铺没有包子，进入等待");
                synchronized (ThreadCommunication.class){
                    LockSupport.park();
                }
            }
            System.out.println("买到包子了");
        });
        thread.start();
        Thread.sleep(100);
        baozi = new ThreadCommunication();
        synchronized (ThreadCommunication.class){
            LockSupport.unpark(thread);
        }
        System.out.println("生产出包子了");
    }

    public static void main(String[] args) throws InterruptedException {
        //测试suspend和resume
        //suspendResume();
        //suspend 不会释放锁，造成死锁
        //suspendResumeDeadLock();
        //suspend 在resume后边执行，线程一直挂起，造成死锁
        //suspendResumeDeadLock1();
        //wait 等待会释放持有的锁
        //waitNotify();
        //wait和notify 需要执行顺序，sleep 不会释放锁
        //waitNotifyDeadLock();
        //park和unpark 解决了方法先后顺序的问题
        //parkUnpark();
        //park方法不会释放锁,造成死锁
        parkUnparkDeadLock();
    }
}
