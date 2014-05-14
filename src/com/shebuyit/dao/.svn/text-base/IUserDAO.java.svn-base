package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.User;

public interface IUserDAO extends GenericDAO<User, String> {
	
	User get(String id);
	
	void save(User user);
	
	List<User> queryUserByQueryCondition(User user, String orderProperty, boolean isAsc, Paging paging);
	
	List<User> queryUser(User user);
	
}
