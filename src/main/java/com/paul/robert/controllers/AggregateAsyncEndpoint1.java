package com.paul.robert.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paul.robert.model.AggregatedMessage;

@RestController
@RequestMapping(value="/async1")
public class AggregateAsyncEndpoint1 {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private Environment env;
	private String baseUrl;
	
	@PostConstruct
	public void init() {
		baseUrl = String.format("http://localhost:%s", env.getRequiredProperty("server.port"));
	}
	
	@GetMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AggregatedMessage> get(){
		Map<String, Object> metricsMap = initiateGet(String.format(baseUrl+"%s", "/metrics")).get();
		Map<String, Object> healthMap = initiateGet(String.format(baseUrl+"%s", "/health")).get();
		
		CompletableFuture.supplyAsync(() -> initiateGet(String.format(baseUrl+"%s", "/metrics")))
			.thenApply((opt) -> {
				return opt.orElseThrow(() -> new IllegalArgumentException("Unable to retrieve data map for metrics"));
			});
			
		//TODO: Compose the completeable futures
		AggregatedMessage msg = null;
//		AggregatedMessage msg = AggregatedMessage.builder()
//			.freeMemory((Integer)metricsMap.get("mem.free"))
//			.totalMemory((Integer)metricsMap.get("mem"))
//			.numOfProcessors((Integer)metricsMap.get("processors"))
//			.operationalStatus((String)healthMap.get("status"))
//			.build();
		
		return ResponseEntity.ok(msg);
	}
	
	@SuppressWarnings("finally")
	private Optional<Map<String, Object>> initiateGet(String url) {
		Optional<Map<String, Object>> retVal = Optional.empty();
		RestTemplate templ = new RestTemplate();
		ResponseEntity<String> resp = templ.exchange(url, HttpMethod.GET, null, String.class);
		try {
			if(resp.getStatusCode().equals(HttpStatus.OK)) {
				Map<String, Object> dataMap = 
						mapper.readValue(resp.getBody(), new TypeReference<Map<String, Object>>(){});
				retVal = Optional.ofNullable(dataMap);
			}
		}catch(RestClientResponseException e) {
			//If we received a client error like a 400 class error!
			//TODO: should accomodate other RESTClientException 
			e.printStackTrace();
		}finally {
			return retVal;
		}
		
	}
	
}
