package com.wy.yubiao.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 10:29
 * @description: BIO服务端 简易服务端,连接会阻塞
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("BIO服务端启动成功");
        while (!serverSocket.isClosed()){
            Socket request = serverSocket.accept();//阻塞
            System.out.println("收到新连接："+request.toString());
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

        }
    }
}
