package com.example.serviceone.controller;

import com.example.serviceone.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AsyncController {

	private final AsyncService asyncService;

	public AsyncController(AsyncService asyncService) {
		this.asyncService = asyncService;
	}

	@GetMapping("/async")
	public String async() {
		log.info("Calling async service!");

		asyncService.process();

		log.info("Called async service!");

		return "Called async service";
	}
}
