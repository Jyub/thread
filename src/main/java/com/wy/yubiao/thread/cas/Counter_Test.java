package com.wy.yubiao.thread.cas;


import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 10:42
 * @description: 测试类
 */
public class Counter_Test {

    public static void main(String[] args) throws InterruptedException {
        CounterCustomize counter = new CounterCustomize();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        for (int i=0; i < threadPool.getCorePoolSize() ; i++){
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "done......");
                    for (int j = 0; j < 10000; j++) {
                        try {
                            counter.increament();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(3000);
        System.out.println(counter.i);
    }
}
