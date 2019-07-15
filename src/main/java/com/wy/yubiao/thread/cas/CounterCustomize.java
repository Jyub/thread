package com.wy.yubiao.thread.cas;




import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/15 11:38
 * @description: 自定义实现Atomic
 */
public class CounterCustomize {
    volatile  int i = 0;
    static Unsafe unsafe;
    static long offset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            Field fieldi =CounterCustomize.class.getDeclaredField("i");
            offset = unsafe.objectFieldOffset(fieldi);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void increament() throws NoSuchFieldException {
        for (;;){
            int current = unsafe.getIntVolatile(this,offset);
            if (unsafe.compareAndSwapInt(this,offset,current,current+1)){
                break;
            }
        }
    }
}
