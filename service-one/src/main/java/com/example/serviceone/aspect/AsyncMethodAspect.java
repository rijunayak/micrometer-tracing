package com.example.serviceone.aspect;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AsyncMethodAspect {

	private final Tracer tracer;

	public AsyncMethodAspect(Tracer tracer) {
		this.tracer = tracer;
	}

	@Before("@annotation(org.springframework.scheduling.annotation.Async)")
	public void beforeAsyncMethod(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();

		Span currentSpan = tracer.currentSpan();
		if (currentSpan != null) {
			currentSpan.name(methodName);
		}
	}
}
