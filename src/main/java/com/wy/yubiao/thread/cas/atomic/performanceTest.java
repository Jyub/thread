package com.wy.yubiao.thread.cas.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 16:54
 * @description: 性能测试
 */
public class performanceTest {
    public int testAtomicInteger(){
        AtomicInteger integer = new AtomicInteger();
        for (int i=0; i<10; i++){
            new Thread(()->{
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < 2000){
                    integer.getAndIncrement();
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer.get();
    }

    public long testLongAccumlator(){
        LongAccumulator a = new LongAccumulator((x,y)->x+y,0L);
        for (int i=0; i<10; i++){
            new Thread(()->{
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < 2000){
                    a.accumulate(1);
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a.get();
    }

    public long testLongAdder(){
        LongAdder l = new LongAdder();
        for (int i=0; i<10; i++){
            new Thread(()->{
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < 2000){
                    l.increment();
                }
            }).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return l.sum();
    }

    @Test
    public void test(){
        System.out.println(testAtomicInteger());
        System.out.println(testLongAccumlator());
        System.out.println(testLongAdder());
    }
}
