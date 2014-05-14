package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IOrderDAO;
import com.shebuyit.po.Orders;

public class OrderDAOImpl extends JPAGenericDAOImpl<Orders, String>
		implements IOrderDAO {

	public List<Orders> queryOrderByQueryCondition(Orders order, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(order);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<Orders> queryOrder(Orders order) {
		String condition = getQueryCondition(order);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(Orders order) {
		String condition = " 1=1 ";

		String orderNumber = order.getOrderNumber();
		if (StringUtils.isNotEmpty(orderNumber)) {
			condition += " and obj.orderNumber ='" +orderNumber+ "'";			
		}
		
		String orderNumber4px = order.getOrderNumber4px();
		if (StringUtils.isNotEmpty(orderNumber4px)) {
			condition += " and obj.orderNumber4px ='" +orderNumber4px+ "'";			
		}
			
		String shipChannel = order.getShipChannel();
		if (StringUtils.isNotEmpty(shipChannel)) {
			condition += " and obj.shipChannel ='" +shipChannel+ "'";			
		}
		String destination = order.getDestination();
		if (StringUtils.isNotEmpty(destination)) {
			condition += " and obj.destination ='" +destination+ "'";			
		}
		String site = order.getSite();
		if (StringUtils.isNotEmpty(site)) {
			condition += " and obj.site ='" +site+ "'";			
		}

		String time_start = order.getTime_start();
		String time_end = order.getTime_end();
		if (StringUtils.isNotEmpty(time_start)&&StringUtils.isNotEmpty(time_end)) {
			condition += " and obj.created_time between '"+time_start+"' and '"+time_end+"' ";
		}else if(StringUtils.isNotEmpty(time_start)&&!StringUtils.isNotEmpty(time_end)){
			condition += " and obj.created_time > '"+time_start+"' ";
		}else if(!StringUtils.isNotEmpty(time_start)&&StringUtils.isNotEmpty(time_end)){
			condition += " and obj.created_time < '"+time_end+"' ";
		}
		
		return condition;
	}

	public Orders get(String id) {
		return super.get(id);
	}

	public void save(Orders order) {
		super.save(order);
	}
}
