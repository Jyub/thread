package com.wy.yubiao.nio;

import java.nio.ByteBuffer;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 15:34
 * @description: NIO Buffer 测试 buffer 三个关键位置 position limit capacity
 */
public class DirectBufferDemo {
    public static void main(String[] args) {
        //分配堆外内存缓冲区,容量是4
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        //查看缓冲区参数
        System.out.println("容量:"+byteBuffer.capacity()+" 限制:"+byteBuffer.limit()+" 位置:"+byteBuffer.position());
        //向缓冲区添加数据
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        System.out.println("容量:"+byteBuffer.capacity()+" 限制:"+byteBuffer.limit()+" 位置:"+byteBuffer.position());
        //转换为读取模式
        byteBuffer.flip();
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.get());
        System.out.println("容量:"+byteBuffer.capacity()+" 限制:"+byteBuffer.limit()+" 位置:"+byteBuffer.position());
        //清空已读数据,再次写入
        byteBuffer.compact();
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        byteBuffer.put((byte) 6);
        System.out.println("容量:"+byteBuffer.capacity()+" 限制:"+byteBuffer.limit()+" 位置:"+byteBuffer.position());
    }
}
