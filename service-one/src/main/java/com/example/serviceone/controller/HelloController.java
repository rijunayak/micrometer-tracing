package com.example.serviceone.controller;

import com.example.serviceone.client.ServiceTwoClient;
import com.example.serviceone.otel.TracingService;
import com.example.serviceone.service.SpaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class HelloController {

	private final ServiceTwoClient serviceTwoClient;
	private final SpaceService spaceService;
	private final TracingService tracingService;

	public HelloController(ServiceTwoClient serviceTwoClient, SpaceService spaceService, TracingService tracingService) {
		this.serviceTwoClient = serviceTwoClient;
		this.spaceService = spaceService;
		this.tracingService = tracingService;
	}

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		log.info("Received request at /api/hello in service-one");
		String response = serviceTwoClient.callServiceTwo();
		log.info("Response from service-two: {}", response);
		return new ResponseEntity<>("Service-one received: " + response, HttpStatus.OK);
	}

	@GetMapping("/helloFromSpace")
	public String helloFromSpace() {
		log.info("Entered hello from space");

		tracingService.runInSpan("connection with space", spaceService::establishConnection);
		String spaceText = tracingService.fetchInSpan("space text", spaceService::getSpaceText);

		log.info("Space Text: {}", spaceText);
		log.info("Hello from space!");
		return "Hello from space!";
	}

	@GetMapping("/helloFromSpaceContext")
	public String helloFromSpaceContext(@RequestParam Map<String, String> queryParams) {
		log.info("Entered hello from space context");

		tracingService.runInSpan("connection with space", spaceService::establishConnection, queryParams);
		String spaceText = tracingService.fetchInSpan("space text", spaceService::getSpaceText, queryParams);

		log.info("Space Context Text: {}", spaceText);
		log.info("Hello from space context!");
		return "Hello from space context!";
	}
}
