package com.wy.yubiao.thread.concurrentutils;

import java.util.concurrent.CyclicBarrier;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/25 16:00
 * @description: 栅栏测试
 */
public class TestCyclicBarrier {

    public static void test01() throws InterruptedException {
        BiaoCyclicBarrier cyclicBarrier = new BiaoCyclicBarrier(3);
        for (int i = 0; i < 24; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在查询数据库");
                }
            }.start();
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test01();
    }
}
