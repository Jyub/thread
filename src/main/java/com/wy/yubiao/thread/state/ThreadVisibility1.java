package com.wy.yubiao.thread.state;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/11 16:51
 * @description: 线程可见性
 */
public class ThreadVisibility1 {

    private  boolean is = true;

    public static void main(String[] args) throws InterruptedException {

        ThreadVisibility1 v = new ThreadVisibility1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i =0;
                System.out.println(String.format("%s开始执行了",Thread.currentThread().getName()));
                while (v.is){
                    synchronized (this){
                        i++;
                    }
                }
                System.out.println(String.format("执行完毕了:%d",i));
            }
        }).start();
        Thread.sleep(3000);
        v.is = false;
        System.out.println("shutdown......");
    }

}
