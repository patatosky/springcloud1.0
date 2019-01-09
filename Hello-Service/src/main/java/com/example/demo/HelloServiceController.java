package com.example.demo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloServiceController {
private final Logger log= Logger.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient client;
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/hello")
	public String index() {
		ServiceInstance serviceInstance = client.getLocalServiceInstance();
		log.info("/hello;主机："+serviceInstance.getHost()+"--服务ID:"+serviceInstance.getServiceId());
		return "hello";
	}
	
}
