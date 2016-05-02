package test;

import com.tanl.kitserver.model.bean.User;
import com.tanl.kitserver.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/5/2.
 */
public class UserTest {

	ApplicationContext context;
	UserService userService;
	//@Resource
	@Before
	public void before() {

		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService)context.getBean("userService");
	}
	@Test
	public void addUser(){

		User user = new User();
		user.setName("cc");
		user.setAge(22);

		userService.insertUser(user);
//		UserDao imp = new UserDaoImp();
//		imp.insert(user);

//		sqlSession.insert("insertUser");
	}
}
