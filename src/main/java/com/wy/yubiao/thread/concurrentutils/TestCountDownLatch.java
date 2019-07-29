package com.wy.yubiao.thread.concurrentutils;

import java.util.concurrent.CountDownLatch;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/25 15:23
 * @description: 测试CountDownLatch
 */
public class TestCountDownLatch {

    public static void test01(){
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println("进入线程"+Thread.currentThread().getName());
                latch.countDown();
                try {
                    latch.await();
                    System.out.println("i am here");
                }catch (Exception e){

                }
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        test01();
    }
}
