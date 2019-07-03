package com.wy.yubiao.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/3 10:29
 * @description: bio客户端
 */
public class BioClient {

    public static final Charset CHARSET =  Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8081);
        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入");
        String next = scanner.nextLine();
        outputStream.write(next.getBytes(CHARSET));
        scanner.close();
        socket.close();

    }
}
