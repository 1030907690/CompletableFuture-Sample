package com.zzq.sample.service;

import com.zzq.sample.utils.BigDecimalUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Zhou Zhongqing
 * @ClassName CompletableFutureService
 * @description: CompletableFuture Service
 * @date 2022-11-14 13:07
 */
@Service
public class CompletableFutureService {


    // 线程池
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    /**
     * 完成了就通知我
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


}
