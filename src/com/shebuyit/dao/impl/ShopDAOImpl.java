package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IShopDAO;
import com.shebuyit.po.Shop;

public class ShopDAOImpl extends JPAGenericDAOImpl<Shop, String>
		implements IShopDAO {

	public List<Shop> queryShopByQueryCondition(Shop shop, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(shop);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<Shop> queryShop(Shop shop) {
		String condition = getQueryCondition(shop);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(Shop shop) {
		String condition = " 1=1 ";
		String shopSku = shop.getShopSku();
		if (StringUtils.isNotEmpty(shopSku)) {
			condition += " and obj.shopSku ='" +shopSku+ "'";			
		}
		String category = shop.getCategory();
		if (StringUtils.isNotEmpty(category)) {
			condition += " and obj.category ='" +category+ "'";			
		}
		String shopBrand = shop.getShopBrand();
		if (StringUtils.isNotEmpty(shopBrand)) {
			condition += " and LOWER(obj.shopBrand) LIKE '%" + StringEscapeUtils.escapeSql(shopBrand).toLowerCase() + "%'";
		}			
		return condition;
	}

	public Shop get(String id) {
		return super.get(id);
	}

	public void save(Shop shop) {
		super.save(shop);
	}

	
}
