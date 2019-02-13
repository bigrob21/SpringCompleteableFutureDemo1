package com.paul.robert.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.robert.utils.Utils;

@RestController
public class RandomizeWaitController {
	private final String aMessage = "Hello after a wait!";
	
	@GetMapping(value="/")
	public ResponseEntity<String> randomizeNumber(){
		Utils.waitOnMe();
		return ResponseEntity.ok(aMessage);
	}
	
}
