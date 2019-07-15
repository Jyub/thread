package com.wy.yubiao.thread;

import org.junit.Test;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/8 11:04
 * @description: list 去重
 */
public class StrListSet {
    private static void removeDuplicate(List<String> list) {
        HashSet<String> set = new HashSet<String>(list.size());
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (set.add(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
    }


    private static void removeDuplicate1(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }

    private static void removeDuplicate2(List<String> list) {
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
    }

    public static void main(String[] args) {
        /*final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            list.add("haha-" + i);
        }

        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            removeDuplicate(list);
        }
        long time1 = System.currentTimeMillis();
        System.out.println("time1:"+(time1-time));

        for (int i = 0; i < 10000; i++) {
            removeDuplicate1(list);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("time2:"+(time2-time1));

        for (int i = 0; i < 10000; i++) {
            removeDuplicate2(list);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("time3:"+(time3-time2));*/

        try {
            Assert.isTrue(false,"我错了");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test() throws Exception {
        Set s = new HashSet();
        s.add("foo");
        Iterator i = s.iterator();
        Method m = i.getClass().getMethod("hasNext", new Class[0]);
        System.out.println(m.invoke(i, new Object[0]));
    }
}
