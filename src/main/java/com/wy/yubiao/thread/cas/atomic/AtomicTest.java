package com.wy.yubiao.thread.cas.atomic;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 15:18
 * @description: 测试
 */
public class AtomicTest {


    @Test
    public void testFloat(){
        AtomicFloat atomicFloat = new AtomicFloat(2.15f);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),new NamedThreadFactory("yubiao"));
        for (int i=0 ; i<10 ; i++){
            executor.submit(()->{
                for (int j=0;j<10;j++){
                    atomicFloat.getAndIncrement();
                }
            });
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(atomicFloat.get());

    }
}
