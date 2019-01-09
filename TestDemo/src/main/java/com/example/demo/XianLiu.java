package com.example.demo;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;


public class XianLiu {
	public static void main(String[] args) {
		XianLiu xl=new XianLiu();
		//单应用限流
//		xl.smoothBursty();
//		xl.smoothWarmUp();
	}
	/**
	 *令牌桶有5个令牌，一秒钟放5个，一个令牌200毫秒，如果突发使用，令牌桶没有那么多的情况 下需要等待，有可能对应用导致不可用
	 *	接口平滑突发限流 
	 * @author wanghui
	 */
	private void smoothBursty(){
		 RateLimiter rateLimiter=RateLimiter.create(5);
		 //取令牌执行
		 System.out.println(rateLimiter.acquire(5));
		 System.out.println(rateLimiter.acquire());
		 System.out.println(rateLimiter.acquire());
		 System.out.println(rateLimiter.acquire());
		 System.out.println(rateLimiter.acquire());
	}
	/**
	 * 固定速率限流
	 * 一秒中放5个，l类似漏斗算法 
	 * 
	 * 
	 */
	private void smoothWarmUp() {
		RateLimiter rateLimiter = RateLimiter.create(5, 1000/*冷启动速率过度平均速率的时间间隔*/, TimeUnit.MILLISECONDS);
		for (int i = 1; i <= 5; i++) {
			System.out.println(rateLimiter.acquire());
		}
		try {
			Thread.sleep(1000L);
			for (int i = 1; i <=5; i++) {
				System.out.println(rateLimiter.acquire());
			}
			
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
