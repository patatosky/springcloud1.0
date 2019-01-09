package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConsumerFeignController {
	
	@Autowired
	HelloService helloService;
	
	@RequestMapping("/consumer-feign")
	public String hello() {
		return helloService.hello();
	}
}
