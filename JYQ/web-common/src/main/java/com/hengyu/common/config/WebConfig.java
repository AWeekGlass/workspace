package com.hengyu.common.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hengyu.common.handler.GlobalExceptionHandler;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Bean
	GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}
	
	/**
	 * 消费队列线程
	 * 
	 * @return
	 */
	@Bean
	public ExecutorService buildConsumerQueueThreadPool() {
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("consumer-queue-thread-%d").build();

		ExecutorService pool = new ThreadPoolExecutor(8, 16, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		return pool;
	}
}
