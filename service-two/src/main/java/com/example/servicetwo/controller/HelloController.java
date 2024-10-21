package com.example.servicetwo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class HelloController {

	@GetMapping("/hello")
	public String sayHello() {
		log.info("Received request at /api/hello in service-two");
		String response = "Hello from service-two!";
		log.info("Sending response: {}", response);
		return response;
	}
}
