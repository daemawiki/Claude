package com.daemawiki.daemawiki.global.utils.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig implements AsyncConfigurer {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        return new VirtualThreadTaskExecutor("deamawiki-virtual-");
    }

    @Bean(destroyMethod = "close")
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor();
    }
}