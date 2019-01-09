package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ConsumerController {
	
	@Autowired
	private  HelloService helloService;
	
	@RequestMapping(value="/ribbon-consumer")
   public String helloConsumer() {
	   return helloService.helloService();
   }
}
