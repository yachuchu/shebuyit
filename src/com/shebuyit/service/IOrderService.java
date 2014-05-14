package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Orders;

public interface IOrderService {
	
	void save(Orders orders);

	Orders get(String id);

	void update(Orders orders);
	
	void remove(String id);
	
	void remove(Orders orders);

	List<Orders> queryOrderByQueryCondition(Orders order,String orderProperty, boolean isAsc,
			Paging paging);
	
	List<Orders> queryOrder(Orders order);
}