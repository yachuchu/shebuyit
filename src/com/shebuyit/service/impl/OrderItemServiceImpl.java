package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IOrderItemDAO;
import com.shebuyit.po.OrderItem;
import com.shebuyit.service.IOrderItemService;
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderItemServiceImpl  implements IOrderItemService {
	private IOrderItemDAO orderItemDAO;


	public void setOrderItemDAO(IOrderItemDAO orderItemDAO) {
		this.orderItemDAO = orderItemDAO;
	}

	@Transactional
	public OrderItem get(String id) {
		return orderItemDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(OrderItem orderItem) {
		orderItemDAO.save(orderItem);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(OrderItem orderItem) {
		orderItemDAO.update(orderItem);
	}
	
	@Transactional
	public void remove(String id) {
		orderItemDAO.remove(id);
	}
	
	@Transactional
	public void remove(OrderItem orderItem) {
		orderItemDAO.remove(orderItem);
	}

	public List<OrderItem> queryOrderItemByQueryCondition(OrderItem orderItem,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.orderItemDAO.queryOrderItemByQueryCondition(orderItem, orderProperty, isAsc, paging);
	}
	
	public List<OrderItem> queryOrderItem(OrderItem orderItem) {
		return orderItemDAO.queryOrderItem(orderItem);
	}
}