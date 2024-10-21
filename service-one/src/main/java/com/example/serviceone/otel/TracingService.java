package com.example.serviceone.otel;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Tracer.SpanInScope;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class TracingService {

	private final Tracer tracer;

	public TracingService(Tracer tracer) {
		this.tracer = tracer;
	}

	public void runInSpan(String spanName, Runnable runnable) {
		runInSpan(spanName, runnable, Collections.emptyMap());
	}

	public <T> T fetchInSpan(String spanName, Supplier<T> supplier) {
		return fetchInSpan(spanName, supplier, Collections.emptyMap());
	}

	public void runInSpan(String spanName, Runnable runnable, Map<String, String> attributes) {
		Span currentSpan = tracer.currentSpan();

		if (currentSpan == null) {
			runnable.run();
		}

		Span newSpan = tracer.nextSpan(currentSpan).name(spanName);

		try (SpanInScope ignored = tracer.withSpan(newSpan.start())) {
			attributes.forEach(newSpan::tag);
			runnable.run();
		} finally {
			newSpan.end();
		}
	}

	public <T> T fetchInSpan(String spanName, Supplier<T> supplier, Map<String, String> attributes) {
		Span currentSpan = tracer.currentSpan();

		if (currentSpan == null) {
			return supplier.get();
		}

		Span newSpan = tracer.nextSpan(currentSpan).name(spanName);

		try (SpanInScope ignored = tracer.withSpan(newSpan.start())) {
			attributes.forEach(newSpan::tag);
			return supplier.get();
		} finally {
			newSpan.end();
		}
	}
}
