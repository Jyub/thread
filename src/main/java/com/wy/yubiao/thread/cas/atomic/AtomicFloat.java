package com.wy.yubiao.thread.cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 14:54
 * @description: 自定义实现AtomicFloat
 */
public class AtomicFloat extends Number{
    private AtomicInteger bits;

    public AtomicFloat(){
        this(0f);
    }

    public AtomicFloat(float f){
        this.bits = new AtomicInteger(Float.floatToIntBits(f));
    }

    public final float addAndGet(float f){
        float except;
        float update;
        do{
            except = get();
            update = except + f;
        }while (!compareAndSet(except,update));
        return update;
    }

    public final float getAndAdd(float f){
        float except;
        float update;
        do{
            except = get();
            update = except + f;
        }while (!compareAndSet(except,update));
        return except;
    }

    public final float getAndDecrement(){
        return getAndAdd(-1);
    }

    public final float decrementAndGet(){
        return addAndGet(-1);
    }

    public final float getAndIncrement(){
        return getAndAdd(1);
    }

    public final float incrementAndGet(){
        return addAndGet(1);
    }

    public final float get(){
       return Float.intBitsToFloat(bits.get());
    }

    public final void set(float f){
        bits.set(Float.floatToIntBits(f));
    }

    public final float getAndSet(float newValue){
        float except;
        do {
            except = get();
        }while (!compareAndSet(except,newValue));
        return except;
    }

    public final boolean compareAndSet(float except,float update){
       return bits.compareAndSet(Float.floatToIntBits(except),Float.floatToIntBits(update));
    }

    @Override
    public int intValue() {
        return (int)get();
    }

    @Override
    public long longValue() {
        return (long)get();
    }

    @Override
    public float floatValue() {
        return get();
    }

    @Override
    public double doubleValue() {
        return (double) get();
    }
}
