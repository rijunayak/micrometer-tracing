package com.example.serviceone.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class TraceparentInterceptor implements HandlerInterceptor {

	private final Tracer tracer;

	public TraceparentInterceptor(Tracer tracer) {
		this.tracer = tracer;
	}

	@SuppressWarnings("NullableProblems")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		Span currentSpan = tracer.currentSpan();

		if (currentSpan != null) {
			TraceContext spanContext = currentSpan.context();
			String traceFlags = Boolean.TRUE.equals(spanContext.sampled()) ? "01" : "00";
			String traceparent = "00-" + spanContext.traceId() + "-" + spanContext.spanId() + "-" + traceFlags;
			response.addHeader("Traceparent", traceparent);
		}

		return true;
	}
}
