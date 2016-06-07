package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

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

	public boolean resetPassword (UserDo userDo) throws SQLException {
		userDo.setUserId(userDo.getUserPhone());
		int colum = sqlMapClient.update("resetPassword", userDo);

		if(colum < 1){
			return false;
		}
		return true;
	}

	public UserDo queryUser (UserDo userDo) throws SQLException {

//		List users = sqlMapClient.queryForList("queryUserInfoAll", userDo);
		List users = sqlMapClientTemplate.queryForList("queryUserInfoAll", userDo);

		System.out.println(users.size());
		return userDo;

	}

	public UserDo queryUserInfo (String userId) throws SQLException {

		return (UserDo) sqlMapClient.queryForObject("getUserInfo", userId);
	}

	public boolean updateUserInfo(UserDo userDo) throws SQLException {
		int line = sqlMapClient.update("updateUserShopInfo", userDo);
		if(line > 0){
			return true;
		}
		return false;
	}

	public boolean addApplication(UserApplication ua) throws SQLException {
		ua.setApplicationState(1);
		Object line = sqlMapClient.insert("addApplication", ua);
		if(null != line){
			return true;
		}
		return false;
	}

	public String queryApplicationState (UserApplication ua) throws SQLException {
		Integer ret = (Integer) sqlMapClient.queryForObject("queryState", ua);
		if(null == ret){
			return "不存在此用户";
		}
		return applicationStateValue(ret);
	}

	private String applicationStateValue(int state) throws SQLException {
		return (String) sqlMapClient.queryForObject("querySateValue", state);
	}
	/**
	 //      ApplicationContext context = new AnnotationConfigApplicationContext(JPAConfiguration.class);
	 //		EntityManagerFactory entityManagerFactory = new EntityManagerFactoryImpl();

	 //		EntityManager entityManager = entityManagerFactory.createEntityManager();
	 //		EntityTransaction entityTransaction = entityManager.getTransaction();
	 //		entityTransaction.begin();
	 //*/
}
