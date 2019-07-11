package com.wy.yubiao.thread.state;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/10 16:41
 * @description: 可见性问题
 */
public class ThreadVisibility {
    static int i=0;
    boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException {
        ThreadVisibility visibility = new ThreadVisibility();
        new Thread(()->{
            System.out.println("开始执行");
           while (visibility.isRunning){
               i++;
           }
            System.out.println("执行结束："+i);
        }).start();

        Thread.sleep(3000);
        visibility.isRunning = false;
        System.out.println("尝试停止。。。。");
    }
}
