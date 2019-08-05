package com.wy.yubiao.thread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Hello hello = new Hello();
        HelloProxy proxy = new HelloProxy(hello);
        IHello proxy1 = (IHello)proxy.proxy();
        proxy1.sayHello();
    }
}
