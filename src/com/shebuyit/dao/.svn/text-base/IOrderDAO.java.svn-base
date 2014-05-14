package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Orders;

public interface IOrderDAO extends GenericDAO<Orders, String> {
	
	Orders get(String id);
	
	void save(Orders order);
	
	List<Orders> queryOrderByQueryCondition(Orders order, String orderProperty, boolean isAsc, Paging paging);
	
	List<Orders> queryOrder(Orders order);
}

