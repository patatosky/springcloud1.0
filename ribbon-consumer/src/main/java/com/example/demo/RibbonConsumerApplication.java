package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
/**
 * 注册服务客户端，实现eureka客户端有服务发现的能力
 * @author wanghui
 *
 */
import org.springframework.web.client.RestTemplate;
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class RibbonConsumerApplication {
	
	/**
	 * 注册模板实例
	 * @LoadBalanced 开启负载均衡
	 * @return
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RibbonConsumerApplication.class, args);
	}
}
