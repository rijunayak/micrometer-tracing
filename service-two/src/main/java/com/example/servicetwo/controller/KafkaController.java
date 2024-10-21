package com.example.servicetwo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaController {

	@KafkaListener(
			id = "message",
			topics = "message")
	public void onMessage(ConsumerRecord<String, String> consumerRecord) {
		log.info("Received Message: {}", consumerRecord.value());
	}
}
