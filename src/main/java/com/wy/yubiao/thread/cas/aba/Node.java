package com.wy.yubiao.thread.cas.aba;

/**
 * @program: thread
 * @author: Yu Biao
 * @create: 2019/07/15 18:59
 * @description: 节点/存储在栈中的对象
 */
public class Node {

    public final String value;
    public Node next;

    public Node(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                '}';
    }
}
