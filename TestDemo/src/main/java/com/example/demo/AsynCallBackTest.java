package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.BasicAsyncResponseConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.nio.reactor.IOReactorException;
import org.assertj.core.util.Lists;
/**
 * 可以支撑大量并发，但是并不能提升性能，实现半异步编排
 * @author wanghui
 *
 */
public class AsynCallBackTest {
	
	
	public static CompletableFuture<String> getHttpData(String url) throws IOReactorException{
		HttpAsyncClient httpAsyncClient= new DefaultHttpAsyncClient();
		CompletableFuture future=new CompletableFuture();
		HttpAsyncRequestProducer producer=HttpAsyncMethods.create(new HttpPost(url));
		BasicAsyncResponseConsumer consumer=new BasicAsyncResponseConsumer();
		FutureCallback back=new FutureCallback<HttpResponse>() {

			@Override
			public void cancelled() {
				future.cancel(true);
			}

			@Override
			public void completed(HttpResponse arg0) {
				System.out.println(arg0.getEntity()+"========="+arg0.getParams());
				future.complete(arg0);
			}

			@Override
			public void failed(Exception arg0) {
				future.completeExceptionally(arg0);
			}
			
		};
		httpAsyncClient.execute(producer, consumer,back);
		return future;
	}
	public static void main(String[] args) throws Exception {
//		AsynCallBackTest test=new AsynCallBackTest();
//		CompletableFuture<String> data = AsynCallBackTest.getHttpData("http://www.baidu.com");
//		System.out.println(data.get());
//==================================
//		JDK8CompleteFuture.test1();
		
		System.out.println(Math.floorMod(6,2));
		System.out.println(Math.floorDiv(4,2));
		System.out.println(new Date());
		
	}
	
}

/**
 * JDK8 异步编排
 * @author wanghui
 *
 */

 class JDK8CompleteFuture{
	 //合并线程处理，非阻塞主线程处理结果
	 public static void test1() throws Exception {
		 List<String> tt=new ArrayList<>();
		 AsynCallBackTest test=new AsynCallBackTest(); 
		 CompletableFuture<String> future1=test.getHttpData("http://www.baidu.com");
		 CompletableFuture<String> future2=test.getHttpData("http://www.baidu.com");
		 CompletableFuture<String> future3=test.getHttpData("http://www.baidu.com");
//		 CompletableFuture.allOf(future1,future2,future3).thenApplyAsync(new Function() {
//
//			@Override
//			public Object apply(Object t) {
//				
//				System.out.println("异步请求执行================！");
//				return t;
//			}
//			 
//		 }).exceptionally(new Function() {
//			@Override
//			public Object apply(Object t) {
//				System.out.println("处理异常=================");
//				return t;
//			}
//			 
//		 });
		 
		 CompletableFuture future4 = CompletableFuture.allOf(future1,future2,future3).thenApplyAsync(new Function() {

				@Override
				public Object apply(Object t) {
					List<String> a=null;
					System.out.println("异步请求执行================！");
					try {
						a = Lists.newArrayList(future1.get(),future2.get(),future3.get());
						tt.addAll(a);
						return a;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return a;
				}
				 
			 }).exceptionally(new Function() {
				@Override
				public Object apply(Object t) {
					System.out.println("处理异常=================");
					return t;
				}
				 
			 });
		 Object object = future4.get();
		 
		 System.out.println(object);
		 
			 
	 }
	 
	 
}




