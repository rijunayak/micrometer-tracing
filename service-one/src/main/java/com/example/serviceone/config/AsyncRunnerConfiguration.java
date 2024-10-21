package com.example.serviceone.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class AsyncRunnerConfiguration implements AsyncConfigurer {

	private final TaskDecorator otelTaskDecorator;

	public AsyncRunnerConfiguration(
			@Qualifier("otelTaskDecorator") TaskDecorator otelTaskDecorator
	) {
		this.otelTaskDecorator = otelTaskDecorator;
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Async-Thread-");
		executor.setTaskDecorator(otelTaskDecorator);
		executor.initialize();
		return executor;
	}
}
