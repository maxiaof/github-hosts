package com.maxiaofa.hosts.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author MaXiaoFa
 */
public class GenContentTheadPoolExecutorConfig {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int corePoolSize = 0;
    private static final int maximumPoolSize = CPU_COUNT * 2 + 1;
    private static final int keepAliveTime = 10;

    public static ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setMaximumPoolSize(maximumPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setKeepAliveTime(keepAliveTime, TimeUnit.SECONDS);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
