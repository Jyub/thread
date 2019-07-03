package com.wy.yubiao.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 10:29
 * @description: BIO服务端 与网页交互,提供正确的http response
 */
public class BioServer2 {

    private static  ExecutorService executor = new ThreadPoolExecutor(0,1000,60, TimeUnit.SECONDS,new SynchronousQueue<>());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        System.out.println("BIO服务端启动成功");
        while (!serverSocket.isClosed()){
            Socket request = serverSocket.accept();//阻塞
            System.out.println("收到新连接："+request.toString());
            executor.execute(()->{
                try {
                    InputStream inputStream = request.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String msg;
                    while ((msg = reader.readLine()) != null){
                        if (msg.length() == 0){
                            break;
                        }
                        System.out.println(msg);
                    }
                    System.out.println("收到新消息，来自"+request.toString());
                    OutputStream outputStream = request.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                    outputStream.write("Hello World".getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        serverSocket.close();
    }
}
