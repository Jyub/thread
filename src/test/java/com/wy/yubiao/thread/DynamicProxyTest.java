package com.wy.yubiao.thread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/1 10:19
 * @description: 动态代理测试
 */
public class DynamicProxyTest {
    interface IHello{
        void sayHello();
    }

    static class Hello implements IHello{
        @Override
        public void sayHello() {
            System.out.println("Hello World");
        }
    }

    static class HelloProxy implements InvocationHandler{
        private Object object;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Welcome");
            return method.invoke(object,args);
        }

        public HelloProxy(Object object){
            this.object = object;
        }

        public Object proxy() {
            return Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
        }
    }

    public static void main(String[] args) {
        /*System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Hello hello = new Hello();
        HelloProxy proxy = new HelloProxy(hello);
        IHello proxy1 = (IHello)proxy.proxy();
        proxy1.sayHello();
        int[] a = new int[]{1,2,3,4};
        int[] b = new int[a.length];
        int num = 0;
        for (int i = 0; i <a.length ; i++) {
            b[i] = a[num++];
            System.out.println(b[i]);
        }*/

        Map map = new HashMap();
        map.put(1, "Hello");
        map.putIfAbsent(1, "World");
        print(map.get(1));
        print(map.size());

        map.put(1024, "A");
        map.putIfAbsent(1024, "B");
        print(map.get(1024));
        print(map.size());

        for (Object entry : map.entrySet()){
            Map.Entry entry1 = (Map.Entry) entry;
            System.out.println(entry1.getKey()+"="+entry1.getValue());
        }
    }

    private static void print(Object object) {
        System.out.print(object + " ");
    }
}
