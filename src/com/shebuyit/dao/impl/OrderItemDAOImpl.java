package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IOrderItemDAO;
import com.shebuyit.po.OrderItem;

public class OrderItemDAOImpl extends JPAGenericDAOImpl<OrderItem, String>
		implements IOrderItemDAO {

	public List<OrderItem> queryOrderItemByQueryCondition(OrderItem orderItem, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(orderItem);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<OrderItem> queryOrderItem(OrderItem orderItem) {
		String condition = getQueryCondition(orderItem);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(OrderItem orderItem) {
		String condition = " 1=1 ";

		String sku = orderItem.getSku();
		if (StringUtils.isNotEmpty(sku)) {
			condition += " and obj.sku ='" +sku+ "'";			
		}
		
		String orderNumber = orderItem.getOrderNumber();
		if (StringUtils.isNotEmpty(orderNumber)) {
			condition += " and obj.orderNumber ='" +orderNumber+ "'";			
		}
			
		String taobaoOrderNumber = orderItem.getTaobao_order_number();
		if (StringUtils.isNotEmpty(taobaoOrderNumber)) {
			condition += " and obj.taobao_order_number ='" +taobaoOrderNumber+ "'";			
		}

		String time_start = orderItem.getTime_start();
		String time_end = orderItem.getTime_end();
		if (StringUtils.isNotEmpty(time_start)&&StringUtils.isNotEmpty(time_end)) {
			condition += " and obj.created_time between '"+time_start+"' and '"+time_end+"' ";
		}else if(StringUtils.isNotEmpty(time_start)&&!StringUtils.isNotEmpty(time_end)){
			condition += " and obj.created_time > '"+time_start+"' ";
		}else if(!StringUtils.isNotEmpty(time_start)&&StringUtils.isNotEmpty(time_end)){
			condition += " and obj.created_time < '"+time_end+"' ";
		}
		
		return condition;
	}

	public OrderItem get(String id) {
		return super.get(id);
	}

	public void save(OrderItem orderItem) {
		super.save(orderItem);
	}
}
