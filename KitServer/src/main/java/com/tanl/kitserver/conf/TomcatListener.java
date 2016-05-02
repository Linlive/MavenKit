package com.tanl.kitserver.conf;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2016/5/3.
 */
public class TomcatListener implements ServletContextListener{

	public void contextInitialized (ServletContextEvent servletContextEvent) {
		System.out.println("............tomcat initial..............");
		new InitServer().createContext();
		System.out.println("............tomcat initialized..............");
	}

	public void contextDestroyed (ServletContextEvent servletContextEvent) {
		System.out.println("tomcat 关闭了..........");
	}
}
