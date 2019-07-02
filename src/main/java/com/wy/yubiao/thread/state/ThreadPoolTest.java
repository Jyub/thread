package com.wy.yubiao.thread.state;

import java.util.List;
import java.util.concurrent.*;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/7/2 15:43
 * @description: 线程池用法
 */
public class ThreadPoolTest {
    private final static int POOL_SIZE = 15;

    /**
     * 创建15个线程,每个线程执行3s,由不同线程池执行,查看创建结果
     * @param executor
     * @throws InterruptedException
     */
    private void test(ThreadPoolExecutor executor) throws InterruptedException {
        for (int i=0; i<POOL_SIZE; i++){
            final int n = i;
            executor.submit(()->{
                try {
                    System.out.println("开始执行任务  "+n);
                    Thread.sleep(3000);
                    System.err.println("任务执行结束  "+n);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(500);
        System.out.println("当前线程池数量:"+executor.getPoolSize());
        System.out.println("当前线程池等待数量:"+executor.getQueue().size());
        Thread.sleep(15000);
        System.out.println("当前线程池数量:"+executor.getPoolSize());
        System.out.println("当前线程池等待数量:"+executor.getQueue().size());
    }

    /**
     * 线程池信息:核心:5,最大:10,存活时间:5,单位:s,无界队列
     * @throws InterruptedException
     */
    private void test1() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        test(executor);

    }

    /**
     * 线程池信息:核心:5,最大:10,存活时间:5,单位:s,队列 3,拒绝策略:拒绝执行
     * @throws InterruptedException
     */
    private void test2() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.err.println("有任务被拒绝了");
            }
        });
        test(executor);

    }

    /**
     * 线程池信息:核心:5,最大:5,存活时间:5,单位:s,无界队列
     * @throws InterruptedException
     */
    private void test3() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,5,5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        //此方法与上边的构造方法等效
        //ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        test(executor);

    }

    /**
     * 线程池信息:核心:0,最大:Integer.MAX_VALUE,存活时间:60,单位:s,同步队列
     * 没有核心线程,有任务时尝试复用空闲线程,复用失败增开新线程执行任务
     * @throws InterruptedException
     */
    private void test4() throws InterruptedException {
        // SynchronousQueue，实际上它不是一个真正的队列，因为它不会为队列中元素维护存储空间。与其他队列不同的是，它维护一组线程，这些线程在等待着把元素加入或移出队列。
        // 在使用SynchronousQueue作为工作队列的前提下，客户端代码向线程池提交任务时，
        // 而线程池中又没有空闲的线程能够从SynchronousQueue队列实例中取一个任务，
        // 那么相应的offer方法调用就会失败（即任务没有被存入工作队列）。
        // 此时，ThreadPoolExecutor会新建一个新的工作者线程用于对这个入队列失败的任务进行处理（假设此时线程池的大小还未达到其最大线程池大小maximumPoolSize）。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0,Integer.MAX_VALUE,60, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        test(executor);
        //等同于 Executors.newCachedThreadPool();
        Thread.sleep(66000);
        System.out.println("执行完任务60s后:"+executor.getPoolSize());

    }

    /**
     * 延迟线程池执行任务,任务只执行一次
     */
    private void test5() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.schedule(()->
                System.out.println("线程开始执行定时任务,当前时间"+System.currentTimeMillis()),
                3000,TimeUnit.MILLISECONDS);
        System.out.println("任务提交成功,线程池数量"+executor.getPoolSize()+"当前时间:"+System.currentTimeMillis());
        Thread.sleep(5000);
        System.out.println("当前线程池状态"+executor.isTerminated());
        executor.shutdown();
        Thread.sleep(500);
        System.out.println("当前线程池状态"+executor.isTerminated());
    }

    /**
     * 循环执行定时任务,当任务执行时间大于周期间隔时，scheduleAtFixedRate方法在执行完当前方法后立即执行下一个任务
     * scheduleWithFixedDelay方法在执行完当前任务之后，在此基础上再增加周期间隔，间隔之后再次执行
     */
    private void test6() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(()->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println("线程开始执行定时任务,当前时间:"+System.currentTimeMillis());},
                3000,1000,TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(()->{
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程开始执行定时任务,当前时间:"+System.currentTimeMillis());},
                3000,1000,TimeUnit.MILLISECONDS);
        System.out.println("任务提交成功,线程池数量"+executor.getPoolSize()+"当前时间:"+System.currentTimeMillis());

    }

    /**
     *核心5，最大10，存活5，队列3，指定拒绝策略
     * shutdown 执行完当前线程及缓冲区任务，拒绝新的任务
     */
    private void test7() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3), (r, executor1) -> System.err.println("有任务被拒绝了"));
        for (int i=0;i<POOL_SIZE;i++){
            final int n=i;
            executor.submit(()->{
                System.out.println("任务开始执行了"+n);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("任务执行结束了"+n);
            });
        }
        Thread.sleep(1000);
        System.out.println("当前线程池数量"+executor.getPoolSize());
        System.out.println("线程池缓冲区数量"+executor.getQueue().size());
        executor.shutdown();
        executor.submit(()->{});
    }

    /**
     *核心5，最大10，存活5，队列3，指定拒绝策略
     * shutdownNow 尝试终止正在执行的线程，拒绝执行缓冲区的任务
     */
    private void test8() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3), (r, executor1) -> System.err.println("有任务被拒绝了"));
        for (int i=0;i<POOL_SIZE;i++){
            final int n=i;
            executor.submit(()->{
                try {
                    System.out.println("任务开始执行了"+n);
                    Thread.sleep(10000);
                    System.err.println("任务执行结束了"+n);
                } catch (InterruptedException e) {
                    System.out.println("异常："+e.getMessage()+n);
                }
            });
        }
        Thread.sleep(5000);
        List<Runnable> runnables = executor.shutdownNow();

        executor.submit(()->{});
        System.out.println("未结束的任务:"+runnables.size());
        for (Runnable runnable : runnables){
            new Thread(runnable).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //new ThreadPoolTest().test1();
        //new ThreadPoolTest().test2();
//        new ThreadPoolTest().test3();
        //new ThreadPoolTest().test4();
        //new ThreadPoolTest().test5();
        //new ThreadPoolTest().test6();
        //new ThreadPoolTest().test7();
        new ThreadPoolTest().test8();
    }
}
