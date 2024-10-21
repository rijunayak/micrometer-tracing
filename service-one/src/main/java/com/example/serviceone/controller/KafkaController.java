package com.example.serviceone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KafkaController {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@GetMapping("/kafka/{key}/{message}")
	public String kafkaMessage(@PathVariable("message") String message,
							   @PathVariable("key") String key) {
		log.info("Sending message to kafka: {}", message);
		kafkaTemplate.send("message", key, message);
		return "Message sent to kafka: " + message;
	}
}
