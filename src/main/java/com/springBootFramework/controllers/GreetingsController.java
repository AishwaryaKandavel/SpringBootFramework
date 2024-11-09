package com.springBootFramework.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {
	
@Autowired
Greeting greeting;

AtomicLong atomicLong = new AtomicLong();
	
	@GetMapping("/greeting")
	public Greeting getGreetings(@RequestParam String name) {
		greeting.setContent("I am learning spring boot from "+name);
		greeting.setId(atomicLong.incrementAndGet());
		return greeting;
	}
}
