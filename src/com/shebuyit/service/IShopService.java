package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Shop;

public interface IShopService {

	void save(Shop shop);

	Shop get(String id);

	void update(Shop shop);

	void remove(String id);

	List<Shop> queryShopByQueryCondition(Shop shop, String orderProperty,boolean isAsc, Paging paging);

	List<Shop> queryShop(Shop shop);
}
