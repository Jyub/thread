package com.wy.yubiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 15:54
 * @description: NIO 客户端
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open();
        socket.configureBlocking(false);//默认阻塞是开启的
        socket.connect(new InetSocketAddress("www.baidu.com",80));
        //没有完成连接,一直等待
        while (!socket.finishConnect()){
            Thread.yield();
        }
       /* Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");*/
        String s = "GET / HTTP/1.1\n\rHost:www.baidu.com\n\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        while (buffer.hasRemaining()){
            socket.write(buffer);
        }
        System.out.println("收到服务器响应");
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        while ( socket.read(buffer1) != -1){
            buffer1.flip();
            if(buffer1.limit() > 0) {
                System.out.println(new String(buffer1.array()));
            }
            buffer1.clear();
        }
        /*buffer1.flip();
        byte[] bytes = new byte[buffer1.limit()];
        buffer1.get(bytes);
        System.out.println(new String(bytes));
        String ss = new String(bytes);
        String[] split = ss.split("\r");
        System.out.println(split.length);
        for (String s1 : split) {
            System.out.println(s1);
        }*/
        socket.close();
    }
}
