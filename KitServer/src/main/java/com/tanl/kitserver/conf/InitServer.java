package com.tanl.kitserver.conf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/5/2.
 */

public class InitServer {
	private ApplicationContext context;
	@Bean
	public ApplicationContext createContext(){
		if(context != null){
			return context;
		}
		synchronized (InitServer.class) {
			if(context == null) {
				context = new ClassPathXmlApplicationContext("applicationContext.xml");
			}
		}
		return context;
	}

//	@Bean(name = "context")
//	public ApplicationContext initContext() {
//		if(context != null){
//			return context;
//		}
//		synchronized (InitServer.class) {
//			if(context == null) {
//				context = new ClassPathXmlApplicationContext("applicationContext.xml");
//			}
//		}
//		return context;
//	}
}
