package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.OrderItem;

public interface IOrderItemService {
	
	void save(OrderItem orderItem);

	OrderItem get(String id);

	void update(OrderItem orderItem);
	
	void remove(String id);
	
	void remove(OrderItem orderItem);

	List<OrderItem> queryOrderItemByQueryCondition(OrderItem orderItem,String orderProperty, boolean isAsc,
			Paging paging);
	
	List<OrderItem> queryOrderItem(OrderItem orderItem);
}