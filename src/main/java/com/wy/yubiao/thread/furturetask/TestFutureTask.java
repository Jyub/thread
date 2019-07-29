package com.wy.yubiao.thread.furturetask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/29 15:06
 * @description:
 */
public class TestFutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallTask ctask = new CallTask();
        BiaoFutureTask<String> ftask = new BiaoFutureTask<>(ctask);


        System.out.println("thread is begin");
        for (int i = 0; i <10 ; i++) {
            new Thread(ftask).start();
            String s = ftask.get();
            System.out.println(s+"==="+i);
        }

        LockSupport.unpark(null);
    }
}
class CallTask implements Callable<String>{
    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        System.out.println("done...");
        return "i am here";
    }
}
