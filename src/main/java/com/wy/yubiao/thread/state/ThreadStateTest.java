package com.wy.yubiao.thread.state;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/1 13:54
 * @description: 线程状态切换
 */
public class ThreadStateTest {
    private static Thread thread1;
    private static ThreadStateTest test;

    public static void main(String[] args) throws InterruptedException {
        //新建->运行->终止
        System.out.println("第一种状态切换新建->运行->终止");
        Thread thread1 = new Thread(()->{
            System.out.println("thread1 当前状态"+Thread.currentThread().getState().toString());
            System.out.println("thread1 执行了");
        });
        System.out.println("thread1 当前状态"+thread1.getState().toString());
        thread1.start();
        Thread.sleep(100);
        System.out.println("thread1 当前状态"+thread1.getState().toString());
        //thread1.start(); TERMINATED状态不能再次调用start方法
        //新建->运行->等待->运行->终止

        System.out.println("第二种状态切换 新建->运行->等待->运行->终止");
        Thread thread = new Thread(()->{
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread 当前状态"+Thread.currentThread().getState().toString());
            System.out.println("thread执行了");
        });
        System.out.println("thread 当前状态"+ thread.getState().toString());
        thread.start();
        System.out.println("thread 当前状态"+ thread.getState().toString());
        Thread.sleep(100);
        System.out.println("thread 当前状态"+ thread.getState().toString());
        Thread.sleep(1410);
        System.out.println("thread 当前状态"+ thread.getState().toString());

        //新建->运行->阻塞->运行->终止
        System.out.println("第三种状态切换 新建->运行->阻塞->运行->终止");
        Thread thread2 = new Thread(()->{
            synchronized (String.class){
                System.out.println("thread2 当前状态"+Thread.currentThread().getState().toString());
                System.out.println("thread2执行了");
            }
        });
        synchronized (String.class){
            System.out.println("thread2 当前状态"+thread2.getState().toString() );
            thread2.start();
            System.out.println("thread2 当前状态"+thread2.getState().toString() );
            Thread.sleep(200);
            System.out.println("thread2 当前状态"+thread2.getState().toString() );
        }
        Thread.sleep(100);
        System.out.println("thread2 当前状态"+thread2.getState().toString() );
    }


}
