package com.example.serviceone.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ServiceTwoClient {

	private final RestTemplate restTemplate;

	public ServiceTwoClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String callServiceTwo() {
		// Use the service name defined in docker-compose.yml instead of localhost
		String url = "http://service-two:8080/api/hello";
		log.info("Calling service-two at URL: {}", url);
		String response = restTemplate.getForObject(url, String.class);
		log.info("Received response from service-two: {}", response);
		return response;
	}
}
