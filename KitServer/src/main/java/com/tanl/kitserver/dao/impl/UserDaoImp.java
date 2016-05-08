package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.UserDO;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/1.
 */

public class UserDaoImp implements UserDao {

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@Resource(name = "sqlMapClientTemplate")
	SqlMapClientTemplate sqlMapClientTemplate;

	public int insert (UserDO user) throws SQLException {

		Object o = sqlMapClient.insert("insertUser", user);
		System.out.println(o.toString());
		return Integer.valueOf(o.toString());
	}

	public UserDO queryUser (UserDO userDo) throws SQLException {

//		List users = sqlMapClient.queryForList("queryUserInfoAll", userDo);
		List users = sqlMapClientTemplate.queryForList("queryUserInfoAll", userDo);

		System.out.println(users.size());
		return userDo;

	}

	public UserDO queryUserInfo (Map map) throws SQLException {

		return (UserDO) sqlMapClient.queryForObject("getUserInfo", map);
	}

	public List<UserDO> queryAllUser (Map map) throws SQLException {

		List userDOs = sqlMapClient.queryForList("getUserInfoAll", map);
		ArrayList<UserDO> arrys = new ArrayList<UserDO>();
		Iterator iterator = userDOs.iterator();
		UserDO userDO = null;
		while (iterator.hasNext()){
			userDO = (UserDO) iterator.next();
			arrys.add(userDO);
		}
		return arrys;
	}
	/**
	 //      ApplicationContext context = new AnnotationConfigApplicationContext(JPAConfiguration.class);
	 //		EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();

	 //		EntityManager entityManager = entityManagerFactory.createEntityManager();
	 //		EntityTransaction entityTransaction = entityManager.getTransaction();
	 //		entityTransaction.begin();
	 //*/
}
