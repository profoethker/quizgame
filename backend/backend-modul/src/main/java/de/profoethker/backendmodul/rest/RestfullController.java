package de.profoethker.backendmodul.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController
 * 
 * @author Miguel
 *
 */
@RestController
public class RestfullController {

	Logger logger = LoggerFactory.getLogger(RestfullController.class);

	@GetMapping("/start")
	public String start() {
		logger.info("Inside start function");
		return "Hello Client";
	}
}
