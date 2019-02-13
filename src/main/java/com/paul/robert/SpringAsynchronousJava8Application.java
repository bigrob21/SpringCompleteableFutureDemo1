package com.paul.robert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class SpringAsynchronousJava8Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringAsynchronousJava8Application.class, args);
	}

	@Bean
	public ObjectMapper appObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper;
	}
}

