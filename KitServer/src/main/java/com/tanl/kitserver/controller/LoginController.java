package com.tanl.kitserver.controller;

import com.tanl.kitserver.model.bean.User;
import com.tanl.kitserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理login-register等请求
 * Created by Administrator on 2016/5/1.
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

	@Autowired
	ApplicationContext context;

//	EntityManagerFactory entityManagerFactory = context.getBean(EntityManagerFactory.class);
//	EntityManager entityManager = entityManagerFactory.createEntityManager();
//	EntityTransaction entityTransaction = entityManager.getTransaction();

	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("got you login");
		UserService userService;
		userService = (UserService)context.getBean("userService");
		User user = new User();
		user.setName("cc");
		user.setAge(22);

		userService.insertUser(user);
//		Query q = entityManager.createQuery("select u from User u where name=:name");
//		q.setParameter("name", "tanlin");
//		entityManager.close();
	}
	@RequestMapping(value = "/register")
	public void register(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("got you register");
//		JSONObject object = Client.readFromClient(request.getReader());

//		System.out.println(object);
//		entityTransaction.begin();
//		User user = new User();
//		user.setName(object.getString("name"));
//		user.setAge(object.getInt("age"));
//
//		entityManager.persist(user);
//		entityTransaction.commit();
//		entityManager.close();
//		object = new JSONObject();
//		object.put("status", "success");
//		Client.writeToClient(response.getWriter(), object);
	}
}
