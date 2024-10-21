package com.example.serviceone.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

	private final TraceparentInterceptor traceparentInterceptor;

	public WebConfig(TraceparentInterceptor traceparentInterceptor) {
		this.traceparentInterceptor = traceparentInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(traceparentInterceptor);
	}
}
