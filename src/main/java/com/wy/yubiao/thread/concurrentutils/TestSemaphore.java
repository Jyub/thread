package com.wy.yubiao.thread.concurrentutils;

import java.util.concurrent.Semaphore;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/25 16:00
 * @description: 信号量测试
 */
public class TestSemaphore {

    public static void test01() {
        BiaoSemaphore semaphore = new BiaoSemaphore(3);
        for (int i = 0; i < 24; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在查询数据库");
                    semaphore.release();
                }
            }.start();
        }
    }

    public static void main(String[] args) {
        test01();
    }
}
