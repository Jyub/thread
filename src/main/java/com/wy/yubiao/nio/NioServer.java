package com.wy.yubiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 16:31
 * @description: NIO 服务端
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(8083));
        System.out.println("服务器已启动,端口:"+channel.getLocalAddress());
        while (true){
            SocketChannel socketChannel = channel.accept();
            if (socketChannel != null){
                System.out.println("收到新连接"+socketChannel.getRemoteAddress());
                socketChannel.configureBlocking(false);
                ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                while (socketChannel.isOpen() && socketChannel.read(requestBuffer) !=-1 ){
                    if (requestBuffer.position() > 0)break;
                }
                if (requestBuffer.position() == 0)continue;
                requestBuffer.flip();
                byte[] bytes = new byte[requestBuffer.limit()];
                requestBuffer.get(bytes);
                System.out.println("收到新消息:"+new String(bytes)+"来自:"+socketChannel.getRemoteAddress());
                String response = "HTTP/1.1 200 OK\r\n"+
                        "Content-Length: 11\r\n\r\n"+
                        "Hello World";
                ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                while (buffer.hasRemaining()){
                    socketChannel.write(buffer);
                }
            }

        }
    }
}
