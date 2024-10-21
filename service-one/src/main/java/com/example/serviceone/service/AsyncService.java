package com.example.serviceone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncService {

	@Async
	public void process() {
		log.info("Async Processing!");
	}
}
