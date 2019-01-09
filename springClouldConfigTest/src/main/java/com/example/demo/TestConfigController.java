package com.example.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

@Controller
@EnableConfigurationProperties({ConfigTestBean.class})
public class TestConfigController {

	
	
    

	
	@Autowired
	@Qualifier("configTestBean")
	private ConfigTestBean bean;


	private List<String> list2;
	
	@RequestMapping("/test")
	@ResponseBody
	public Object getTest() {
      FF f=new FF();
    Thread a1=new Thread(f) ;
    Thread a2=new Thread(f) ;
    Thread a3=new Thread(f) ;
    Thread a4=new Thread(f) ;
	a1.start();	
	a2.start();	
	a3.start();	
	a4.start();	
		
		return "成功";
	}
	
}

class FF implements Runnable{
	private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
	@Override
	public void run() {
		  //获取库存状态
	       ListenableFuture<List<String> > task1 = service.submit(new Callable<List<String> >() {
	           @Override
	           public List<String>  call() throws Exception {
	        	   List<String> sts=Lists.newArrayList();
	        	   for (int i = 0; i < 100; i++) {
	        		   sts.add(i+"");
				}
	               return sts ;
	           }
	       });
	       // 统计应收合计
	       ListenableFuture<List<String> > task2 = service.submit(new Callable<List<String> >() {
	           @Override
	           public List<String>  call() throws Exception {
	        	   List<String> sts=Lists.newArrayList();
	        	   for (int i = 100; i < 200; i++) {
	        		   sts.add(i+"");
				}
	               return sts ;
	           }
	       });
	       
	       Futures.addCallback(task1, new FutureCallback<List<String>>() {
	           @Override
	           public void onSuccess(List<String>  list) {
	        	   System.out.println("task1 执行成功");
	           }

	           @Override
	           public void onFailure(Throwable t) {
	        		System.out.println("失败了1");
	               t.printStackTrace();
	           }
	       });
	       
	       Futures.addCallback(task2, new FutureCallback<List<String>>() {
	           @Override
	           public void onSuccess(List<String>  list) {
	        	   System.out.println("task2 执行成功");

	           }

	           @Override
	           public void onFailure(Throwable t) {
	       		System.out.println("失败了2");

	               t.printStackTrace();
	           }
	       });
	       List<String> list=null;
	       List<String> list2=null;
	       try {
	    	   list2 = task2.get();
	    	   System.out.println("2拿到数据"+list2.toString());
	    	   
			 list = task1.get();
			 System.out.println("1拿到数据"+list.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       List<String> returnList=Lists.newArrayList(); 
	      
	       returnList.addAll(list);
	       returnList.addAll(list2);
	       System.out.println(returnList.toString()+"==================================");
	       
	}
	
}



