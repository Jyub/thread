package com.wy.yubiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 16:31
 * @description: NIO 服务端
 */
public class NioServer2 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(8083));

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);



        System.out.println("服务器已启动,端口:"+channel.getLocalAddress());
        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel clientSocketChannel = server.accept();
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector,SelectionKey.OP_READ);
                    System.out.println("收到新连接："+clientSocketChannel.getRemoteAddress());
                }
                if (selectionKey.isReadable()){
                    SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (clientSocketChannel.isOpen() && (clientSocketChannel.read(buffer)) != -1){
                            if (buffer.position() > 1)break;
                        }
                        if (buffer.position() == 0) continue;
                        buffer.flip();
                        byte[] bytes = new byte[buffer.limit()];
                        buffer.get(bytes);
                        System.out.println(new String(bytes));
                        System.out.println("收到消息来自："+clientSocketChannel.getRemoteAddress());
                        String response = "HTTP/1.1 200 OK\r\n"+
                                "Content-Length: 11\r\n\r\n"+
                                "Hello World";
                        ByteBuffer buffer1 = ByteBuffer.wrap(response.getBytes());
                        while (buffer1.hasRemaining()){
                            clientSocketChannel.write(buffer1);
                        }
                    }catch (Exception e){
                        selectionKey.cancel();
                    }
                }
            }

        }
    }
}
