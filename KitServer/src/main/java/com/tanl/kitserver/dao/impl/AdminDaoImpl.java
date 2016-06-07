package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.AdminDao;
import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/21.
 */
public class AdminDaoImpl implements AdminDao {

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@Resource(name = "sqlMapClientTemplate")
	SqlMapClientTemplate sqlMapClientTemplate;

	public AdminDo queryAdminInfo (AdminDo adminDo) throws SQLException {

		return (AdminDo)sqlMapClient.queryForObject("queryAdmin", adminDo);
	}

	public Integer updateAdminInfo (AdminDo adminDo) throws SQLException {

		Integer a = sqlMapClient.update("updateAdmin", adminDo);
		return a;
	}

	public List<UserApplication> queryApplication () throws SQLException {
		List daos = sqlMapClient.queryForList("queryApplicationByState", 1);
		return change(daos);
	}
	private List<UserApplication> change(List daos){
		List<UserApplication> applications = new LinkedList<UserApplication>();
		for (Object dao : daos) {
			UserApplication o = (UserApplication) dao;
			applications.add(o);
		}
		return applications;
	}

	/**
	 * 处理用户的申请，拒绝或同意
	 * @param applicationId
	 * @param accept
	 * @return
	 * @throws SQLException
	 */
	public boolean handleApplication (long applicationId, boolean accept) throws SQLException {
		int daoLine;
		if(accept){
			daoLine = sqlMapClient.update("acceptApplication", applicationId);
		}else {
			daoLine = sqlMapClient.update("rejectApplication", applicationId);
		}

		if(daoLine >= 1){
			return true;
		}
		return false;
	}

	public ArrayList<UserDo> queryAllUser () throws SQLException {
		List userDOs = sqlMapClient.queryForList("getAllUser");
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
	 * 删除用户
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteUser (String userId) throws SQLException {
		int daoLine = sqlMapClient.delete("deleteUserById", userId);
		if(daoLine >= 1){
			return true;
		}
		return false;
	}
}
