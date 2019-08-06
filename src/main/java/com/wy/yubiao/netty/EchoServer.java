package com.wy.yubiao.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/6 17:06
 * @description: netty服务端
 */
public class EchoServer {
    static final int PORT = Integer.parseInt(System.getProperty("port","8080"));
    public static void main(String[] args) throws Exception{
        //创建EventLoopGroup accept线程组NioEventLoop
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建EventLoopGroup I/O线程组
        EventLoopGroup workGroup = new NioEventLoopGroup(1);
        try {
            //服务端启动引导工具类
            ServerBootstrap b = new ServerBootstrap();
            //配置服务端出来的reactor线程组及服务端其他配置
            b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel  channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new EchoServerHandler());
                }
            });
            //通过bind绑定端口启动服务
            ChannelFuture future = b.bind(PORT).sync();
            //阻塞主线程直到网络服务被关闭
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭线程组
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
