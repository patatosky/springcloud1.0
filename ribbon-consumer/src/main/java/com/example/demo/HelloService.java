package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {
	
	@Autowired
	RestTemplate restTemplate;
	/**
	 * 通过模板的负载均衡访问服务接口
	 * @return
	 */
	@HystrixCommand(fallbackMethod="helloFallback")
	public String helloService() {
		return restTemplate.getForEntity("HTTP://HELLO-SERVICE/hello", String.class).getBody();
	}
	
	public String helloFallback() {
		return "error";
	}
	
}
