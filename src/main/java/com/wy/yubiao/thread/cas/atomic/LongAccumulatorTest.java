package com.wy.yubiao.thread.cas.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 16:50
 * @description: LongAccumulator
 */
public class LongAccumulatorTest {

    @Test
    public void test(){
        LongAccumulator accumulator = new LongAccumulator((x,y)->{
            return x+y;
        },0L);
        accumulator.accumulate(10);
        System.out.println(accumulator.get());

    }

}
