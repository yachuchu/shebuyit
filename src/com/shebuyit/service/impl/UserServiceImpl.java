package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IUserDAO;
import com.shebuyit.po.User;
import com.shebuyit.service.IUserService;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements IUserService {
	private IUserDAO userDAO;
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User get(String id) {
		return userDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(User user) {
		userDAO.save(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(User user) {
		userDAO.update(user);
	}
	
	@Transactional
	public void remove(String id) {
		userDAO.remove(id);
	}

	public List<User> queryUserByQueryCondition(User user,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.userDAO.queryUserByQueryCondition(user, orderProperty, isAsc, paging);
	}

	public List<User> queryUser(User user) {
		return this.userDAO.queryUser(user);
	}
}