package com.wy.yubiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 16:31
 * @description: NIO 服务端
 */
public class NioServer1 {

    private static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(8083));
        System.out.println("服务器已启动,端口:"+channel.getLocalAddress());
        while (true){
            SocketChannel socketChannel = channel.accept();
            if (socketChannel != null){
                System.out.println("收到新连接"+socketChannel.getRemoteAddress());
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }else {
                Iterator<SocketChannel> iterator = channelList.iterator();
                while (iterator.hasNext()){
                    SocketChannel sc = iterator.next();
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    if (sc.read(requestBuffer) == 0)continue;
                    while (sc.isOpen() && sc.read(requestBuffer) !=-1 ){
                        if (requestBuffer.position() > 0)break;
                    }

                    requestBuffer.flip();
                    byte[] bytes = new byte[requestBuffer.limit()];
                    requestBuffer.get(bytes);
                    System.out.println("收到新消息:"+new String(bytes)+"来自:"+sc.getRemoteAddress());
                    String response = "HTTP/1.1 200 OK\r\n"+
                            "Content-Length: 11\r\n\r\n"+
                            "Hello World";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()){
                        sc.write(buffer);
                    }
                    iterator.remove();
                }
            }

        }
    }
}
