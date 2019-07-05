package com.wy.yubiao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/4 16:24
 * @description: selector 多路复用reactor线程模型
 */
public class NioServer3 {
    //创建工作线程池
    private ExecutorService workPool = Executors.newCachedThreadPool();

    abstract class ReactorThread extends Thread{
        Selector selector;
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

        public  ReactorThread() throws IOException{
            selector = Selector.open();
        }

        public abstract void handler(SelectableChannel channel) throws Exception;

        volatile  boolean running = false;

        @Override
        public void run(){
            while (running){
                try {
                    Runnable task;
                    while ((task = taskQueue.poll()) != null){
                        task.run();
                    }
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        // 被封装的查询结果
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        int readyOps = key.readyOps();
                        // 关注 Read 和 Accept两个事件
                        if ((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT)) != 0 || readyOps == 0) {
                            try {
                                SelectableChannel channel = key.channel();
                                channel.configureBlocking(false);
                                handler(channel);
                                if (!channel.isOpen()) {
                                    key.cancel(); // 如果关闭了,就取消这个KEY的订阅
                                }
                            } catch (Exception ex) {
                                key.cancel(); // 如果有异常,就取消这个KEY的订阅
                            }
                        }


                    }
                    selector.selectNow();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

        private SelectionKey register(SelectableChannel channel) throws Exception {
            // 为什么register要以任务提交的形式，让reactor线程去处理？
            // 因为线程在执行channel注册到selector的过程中，会和调用selector.select()方法的线程争用同一把锁
            // 而select()方法是在eventLoop中通过while循环调用的，争抢的可能性很高，为了让register能更快的执行，就放到同一个线程来处理
            FutureTask<SelectionKey> futureTask = new FutureTask<>(() -> channel.register(selector, 0));
            taskQueue.add(futureTask);
            return futureTask.get();
        }

        private void  doStart(){
            if (!running){
                running = true;
                start();
            }
        }

    }

    private ServerSocketChannel serverSocketChannel;
    //处理网络请求
    private ReactorThread[]  mainReactor = new ReactorThread[1];
    //处理I/O线程
    private ReactorThread[]  subReactor = new ReactorThread[8];

    /**
     * 初始化线程
     * @throws Exception
     */
    public void initReactor() throws Exception{
        for (int i=0; i<subReactor.length;i++){
            subReactor[i] = new ReactorThread() {
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    SocketChannel sc = (SocketChannel) channel;
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (sc.isOpen() && sc.read(buffer) != -1){
                        if (buffer.position() > 0)break;
                    }
                    if(buffer.position() == 0)return;
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    buffer.get(bytes);
                    System.out.println(new String(bytes));
                    System.out.println(Thread.currentThread().getName()+"收到新消息来自:"+sc.getRemoteAddress());

                    workPool.submit(()->{});

                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Hello World";
                    buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()){
                        sc.write(buffer);
                    }
                }
            };
        }

        //主要负责线程的分发
        for (int i=0; i<mainReactor.length;i++){
            mainReactor[i] = new ReactorThread() {
                AtomicInteger inc = new AtomicInteger(0);
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //分发
                    int index = inc.getAndIncrement() % subReactor.length;
                    ReactorThread workEventLoop = subReactor[index];
                    workEventLoop.doStart();
                    SelectionKey register = workEventLoop.register(socketChannel);
                    register.interestOps(SelectionKey.OP_READ);
                    System.out.println(Thread.currentThread().getName()+"收到新连接:"+socketChannel.getRemoteAddress());
                }
            };
        }
    }

    private void initAndRegister() throws Exception {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        int index = new Random().nextInt(mainReactor.length);
        ReactorThread reactorThread = mainReactor[index];
        reactorThread.doStart();
        reactorThread.register(serverSocketChannel);
        serverSocketChannel.register(reactorThread.selector,SelectionKey.OP_ACCEPT);
    }


    private void bind()throws IOException{
        serverSocketChannel.bind(new InetSocketAddress(8083));
        System.out.println("服务已启动"+serverSocketChannel.getLocalAddress());
    }

    public static void main(String[] args) {
        NioServer3 server3 = new NioServer3();

        try {
            server3.initReactor();
            server3.initAndRegister();
            server3.bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
