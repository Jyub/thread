package com.wy.yubiao.thread.cas.aba;

import java.util.concurrent.locks.LockSupport;

/**
 * @program: thread
 * @author: yubiao
 * @create: 2019/07/15 19:52
 * @description: 测试
 */
public class Test {

    public static void main(String[] args) {
        Stack stack = new Stack();

        stack.push(new Node("B"));
        stack.push(new Node("A"));

        Thread thread = new Thread(()->{
           Node node = stack.pop(800);
            System.out.println(Thread.currentThread().getName()+"  "+node.toString());
            System.out.println("done...");
        });
        thread.start();

        Thread thread1 = new Thread(()->{
            LockSupport.parkNanos(1000*1000*300);

            Node nodeA = stack.pop(0);
            System.out.println(Thread.currentThread().getName()  +" "+  nodeA.toString());
            Node nodeB = stack.pop(0);      //取出B，之后B处于游离状态
            System.out.println(Thread.currentThread().getName()  +" "+  nodeB.toString());

            stack.push(new Node("C"));
            stack.push(new Node("D"));
            stack.push(nodeA);
            System.out.println("done...");
        });

        thread1.start();

        LockSupport.parkNanos(1000 * 1000 * 1000 * 2L);


        System.out.println("开始遍历Stack：");
        Node node = null;
        while ((node = stack.pop(0))!=null){
            System.out.println(node.value);
        }


    }

}
