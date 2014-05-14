package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.OrderItem;

public interface IOrderItemDAO extends GenericDAO<OrderItem, String> {
	
	OrderItem get(String id);
	
	void save(OrderItem orderItem);
	
	List<OrderItem> queryOrderItemByQueryCondition(OrderItem orderItem, String orderProperty, boolean isAsc, Paging paging);
	
	List<OrderItem> queryOrderItem(OrderItem orderItem);
}