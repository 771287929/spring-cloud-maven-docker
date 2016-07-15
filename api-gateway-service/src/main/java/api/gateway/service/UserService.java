/*
 * Copyright 2012-2020 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * @author lzhoumail@126.com/zhouli
 * Git http://git.oschina.net/zhou666/spring-cloud-7simple
 */
package api.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserService {
	 @Autowired	 
	 RestTemplate restTemplate;
	
	 final String SERVICE_NAME="simpleserver";
	 
	 @HystrixCommand(fallbackMethod = "fallbackSearchAll")
	 public String readUserInfo() {
	        return restTemplate.getForObject("http://"+SERVICE_NAME+"/user", String.class);
	 }	 
	 private String fallbackSearchAll() {
		 return "HystrixCommand fallbackMethod handle!";
	 }
}
