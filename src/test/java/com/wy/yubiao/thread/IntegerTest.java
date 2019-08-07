package com.wy.yubiao.thread;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/12 16:32
 * @description: TODO
 */
public class IntegerTest {

    private static final int d = 0;

    public static void main(String[] args) throws IllegalAccessException {
        HashMap map = new HashMap(1000);
        map.put(123, 123);
        Field[] fields = map.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.println(field.getName());
            if (true) {
                if (field.getType().isArray()){
                    Object[] obj = (Object[])(field.get(map));
                    for (Object o : obj) {
                        if (o != null){
                            Field[] declaredFields = o.getClass().getDeclaredFields();
                            for (Field declaredField : declaredFields) {
                                field.setAccessible(true);
                                System.out.println(declaredField.getName()+"==="+declaredField.get(o));
                            }
                        }
                    }
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    System.out.println(field.getName() + "::::" + field.get(null));
                }else {
                    System.out.println(field.getName() + "::::" + field.get(map));
                }
            }
        }


    }

}
