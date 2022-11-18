package com.zzq.sample.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Zhou Zhongqing
 * @ClassName CompletableFutureService
 * @description: CompletableFuture Service
 * @date 2022-11-14 13:07
 */
@Service
public class CompletableFutureService {

    private final int EXCEPTION_PARAM = 10;

    // 线程池
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    /**
     * 完成了就通知我 ，手动
     *
     * @return
     */
    public String completeNotify() {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        threadPoolTaskExecutor.execute(new AskThread(future));
        try {
            Integer result = future.get();
            System.out.println("result " + result);
            return result.toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    class AskThread implements Runnable {
        CompletableFuture<Integer> future;

        public AskThread(CompletableFuture<Integer> future) {
            this.future = future;
        }

        @Override
        public void run() {
            int res = 0;
            try {
                // 模拟长时间计算过程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            res = 100;
            // 通知完成
            future.complete(res);

        }
    }


    /**
     * 异步执行任务
     */

    public String asyncTask() {
        StopWatch stopWatch = new StopWatch("asyncTask");
        stopWatch.start("task");
        // 如果是runAsync 没有返回值
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(50), threadPoolTaskExecutor);

        CompletableFuture<Integer> futureTwo = CompletableFuture.supplyAsync(() -> calc(60), threadPoolTaskExecutor);
        int result = 0;
        int res = 0;
        try {
            result = future.get();
            res = futureTwo.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


        System.out.println(result + " " + res);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getLastTaskTimeMillis());

        return result + " " + res;
    }

    public int calc(int param) {


        try {
            // 模拟耗时
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (EXCEPTION_PARAM == param){
            throw new RuntimeException("传了异常参数 "+param);
        }
        return param * 2;
    }

    /**
     * 流式调用
     */
    public String stream() {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> calc(50), threadPoolTaskExecutor).
                thenApply((i) -> Integer.toString(i)).
                thenApply((str) -> "res " + str).
                thenAccept(System.out::println);
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return "done";
    }


    /**
     * 异常处理
     */
    public String exception() {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> calc(10))
                .exceptionally(ex -> {
                    System.out.println("异常信息 " + ex.toString());
                    return 0;
                })
                .thenApply((i) -> Integer.toString(i)).
                thenApply((str) -> "res " + str).
                thenAccept(System.out::println);
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


        return "done";
    }


    /**
     * 组合多个CompletableFuture
     */
    public String compose(){

        CompletableFuture future = CompletableFuture.supplyAsync(()->calc(50),threadPoolTaskExecutor)
                .thenCompose((i)->CompletableFuture.supplyAsync(()->calc(i),threadPoolTaskExecutor))
                .thenApply((str)->"res " + str)
                .thenAccept(System.out::println);
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return "done";
    }



}
