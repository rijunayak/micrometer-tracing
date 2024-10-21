package com.example.serviceone.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskDecorator;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableAsync
@EnableKafka
@Import({AsyncRunnerConfiguration.class})
public class AppConfig {

	private final Tracer tracer;

	public AppConfig(Tracer tracer) {
		this.tracer = tracer;
	}

	@Bean("otelTaskDecorator")
	public TaskDecorator otelTaskDecorator() {
		return runnable -> {
			Span parentSpan = tracer.currentSpan();

			if (parentSpan == null) {
				return runnable;
			}

			Span newSpan = tracer.nextSpan(parentSpan);

			return () -> {
				try (Tracer.SpanInScope ignored = tracer.withSpan(newSpan.start())) {
					runnable.run();
				} finally {
					newSpan.end();
				}
			};
		};
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
		KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory);
		template.setObservationEnabled(true);
		return template;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
}
