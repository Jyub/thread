package com.wy.yubiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/6 17:32
 * @description: TODO
 */
public class NioClient1 {
    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open();
        socket.configureBlocking(false);//默认阻塞是开启的
        socket.connect(new InetSocketAddress("127.0.0.1",8080));
        //没有完成连接,一直等待
        while (!socket.finishConnect()){
            Thread.yield();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");
        String s = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
        while (buffer.hasRemaining()){
            socket.write(buffer);
        }
        System.out.println("收到服务器响应");
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        while (socket.isOpen() && socket.read(buffer1) != -1){
            if (buffer1.position() > 0)break;
        }
        buffer1.flip();
        byte[] bytes = new byte[buffer1.limit()];
        buffer1.get(bytes);
        System.out.println(new String(bytes));
        socket.close();
    }
}
