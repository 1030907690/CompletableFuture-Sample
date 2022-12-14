package com.zzq.sample.conf;

import com.zzq.sample.utils.BigDecimalUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author ruoyi
 **/
@Configuration
public class ThreadPoolConfig {
    // 核心线程池大小
    private int corePoolSize = calculateThreadNumber();

    // 最大可创建的线程数
    private int maxPoolSize = 200;

    // 队列最大长度
    private int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 300;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("taskExecutor-");
        return executor;
    }

    private int calculateThreadNumber() {
        //  Nthreads = Ncpu x Ucpu x (1 + W/C)  计算线程数
        // 例如：平均每个线程计算运行时间为0.5s，而线程等待时间（非计算时间，比如IO）为0.8s，目标CPU的使用率是80%，CPU核心数为12，那么根据上面这个公式估算得到：12 * 0.8 (1 + 0.8/0.5)  约等于 24。
        BigDecimal left = BigDecimalUtils.multiply(new BigDecimal(Double.toString(0.8)), Runtime.getRuntime().availableProcessors());
        BigDecimal right = BigDecimalUtils.round(0.8, 0.5).add(new BigDecimal(1));
        return left.multiply(right).intValue();
    }



}
