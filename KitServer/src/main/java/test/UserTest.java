package test;

import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.ClientInfoObj;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

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


		UserDo user = new UserDo();

		user.setUserName("ccaaaa");
		user.setUserPassword("mima");
		user.setUserAge(22);
		user.setUserId("2012051045");
		user.setUserPhone("110");
		user.setUserEmail("email");
		System.out.println(user.getUserId());

		ServiceResult serviceResult = userService.insertUser(user);

		Admin admin = new Admin();
		admin.setName("admin");
		admin.setPassword("");


	}
	@Test
	public void findUser(){

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userName", "cc");
		ServiceResult<UserDo> dao = userService.queryUserInfo(map);
		Logger logger = LoggerFactory.getLogger(UserTest.class);
//		System.out.println(result.getData() + "========" + result.isSuccess());

		System.out.println(dao.getData().getUserName());
		System.out.println("------------------------------------------------------" + this.toString() + "message");
	}


	private class Admin {
		String name;
		String password;

		public String getName () {

			return name;
		}

		public void setName (String name) {

			this.name = name;
		}

		public String getPassword () {

			return password;
		}

		public void setPassword (String password) {

			this.password = password;
		}
	}
	@Test
	public void gsonTest(){

		JSONObject object = new JSONObject();
		ClientInfoObj<JSONObject> clientInfoObj = new ClientInfoObj<JSONObject>();

		clientInfoObj.setOperateSuccess(true);
		clientInfoObj.setDigestMessage("this is message");
		clientInfoObj.setErrorCode(404);

		object.put("jsonName1", "name1");
		clientInfoObj.setData(object);


		JSONObject jsonObject = clientInfoObj.getData();

		System.out.println(jsonObject);

	}
}
