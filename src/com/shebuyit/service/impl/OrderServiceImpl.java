package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IOrderDAO;
import com.shebuyit.po.Orders;
import com.shebuyit.service.IOrderService;
/**
 * @author chuyazhou
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl implements IOrderService {
	private IOrderDAO orderDAO;


	public void setOrderDAO(IOrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Transactional
	public Orders get(String id) {
		return orderDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Orders order) {
		orderDAO.save(order);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Orders order) {
		orderDAO.update(order);
	}
	
	@Transactional
	public void remove(String id) {
		orderDAO.remove(id);
	}
	
	@Transactional
	public void remove(Orders order) {
		orderDAO.remove(order);
	}

	public List<Orders> queryOrderByQueryCondition(Orders order,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.orderDAO.queryOrderByQueryCondition(order, orderProperty, isAsc, paging);
	}
	
	public List<Orders> queryOrder(Orders order) {
		return orderDAO.queryOrder(order);
	}
}