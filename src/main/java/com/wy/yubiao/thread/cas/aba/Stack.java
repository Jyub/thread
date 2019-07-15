package com.wy.yubiao.thread.cas.aba;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: thread
 * @author: yubiao
 * @create: 2019/07/15 19:10
 * @description: 栈，存储节点对象
 */
public class Stack {
    //top cas 无锁修改
    AtomicReference<Node> top = new AtomicReference<>();

    /**
     * 出栈--去除栈顶，为了演示ABA效果，增加一个CAS操作的延时
     * @param time
     * @return
     */
    public Node pop(int time){

        Node newTop;
        Node oldTop;
        do{
            oldTop = top.get();
            if (oldTop == null){
                return null;
            }
            newTop = oldTop.next;
            if (time != 0){
                LockSupport.parkNanos(1000*1000*time);
            }
        }while (!top.compareAndSet(oldTop,newTop));
        return oldTop;
    }

    /**
     * 入栈
     * @param node
     */
    public void push(Node node){
        Node oldTop;
        do{
            oldTop = top.get();
            node.next = oldTop;
        }while (!top.compareAndSet(oldTop,node));
    }
}
