package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IUserDAO;
import com.shebuyit.po.User;

public class UserDAOImpl extends JPAGenericDAOImpl<User, String> implements IUserDAO {

	public List<User> queryUserByQueryCondition(User user, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(user);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<User> queryUser(User user) {
		String condition = getQueryCondition(user);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(User user) {
		String condition = " 1=1 ";
		String userName = user.getUserName();
		if (StringUtils.isNotEmpty(userName)) {
			condition += " and obj.userName ='" +userName+ "'";		
		}
		String type = user.getType();
		if (StringUtils.isNotEmpty(type)) {
			condition += " and obj.type ='" +type+ "'";			
		}		
		return condition;
	}

	public User get(String id) {
		return super.get(id);
	}

	public void save(User user) {
		super.save(user);
	}

	
}
