package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.UserDo;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/1.
 */

public class UserDaoImp extends SqlMapClientDaoSupport implements UserDao {

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@Resource(name = "sqlMapClientTemplate")
	SqlMapClientTemplate sqlMapClientTemplate;

	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}

	public int insert (UserDo user) throws SQLException {

		Object o = sqlMapClient.insert("insertUser", user);
		System.out.println(o.toString());
		return Integer.valueOf(o.toString());
	}

	public UserDo queryUser (UserDo userDo) throws SQLException {

//		List users = sqlMapClient.queryForList("queryUserInfoAll", userDo);
		List users = sqlMapClientTemplate.queryForList("queryUserInfoAll", userDo);

		System.out.println(users.size());
		return userDo;

	}

	public UserDo queryUserInfo (Map map) throws SQLException {

		return (UserDo) sqlMapClient.queryForObject("getUserInfo", map);
	}

	public List<UserDo> queryAllUser (Map map) throws SQLException {

		List userDOs = sqlMapClient.queryForList("getAllUser", map);
		ArrayList<UserDo> arrys = new ArrayList<UserDo>();
		Iterator iterator = userDOs.iterator();
		UserDo userDo = null;
		while (iterator.hasNext()){
			userDo = (UserDo) iterator.next();
			arrys.add(userDo);
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
