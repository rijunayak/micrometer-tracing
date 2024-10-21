package com.example.servicetwo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
@Slf4j
public class AppConfig {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();

		factory.getContainerProperties().setObservationEnabled(true);
		factory.setConsumerFactory(consumerFactory);

		return factory;
	}
}
