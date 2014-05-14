package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Shop;

public interface IShopDAO extends GenericDAO<Shop, String> {
	
	Shop get(String id);
	
	void save(Shop shop);
	
	List<Shop> queryShopByQueryCondition(Shop shop, String orderProperty, boolean isAsc, Paging paging);
	
	List<Shop> queryShop(Shop shop);


}
