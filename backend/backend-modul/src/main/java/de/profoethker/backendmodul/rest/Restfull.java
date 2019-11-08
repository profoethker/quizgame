package de.profoethker.backendmodul.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Restfull {

	@GetMapping("/start")
	public String start(){
		System.out.println("Inside start function");
		return "Hello Client";
	}
}
