package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.User;

public interface IUserService {

	void save(User user);

	User get(String id);

	void update(User user);

	void remove(String id);

	List<User> queryUserByQueryCondition(User user, String orderProperty,boolean isAsc, Paging paging);

	List<User> queryUser(User user);
}

