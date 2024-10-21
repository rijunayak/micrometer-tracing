package com.example.serviceone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpaceService {

	public void establishConnection() {
		log.info("Establishing connection with space!");

		log.info("Established connection with space!");
	}

	public String getSpaceText() {
		return "Space Text";
	}
}
