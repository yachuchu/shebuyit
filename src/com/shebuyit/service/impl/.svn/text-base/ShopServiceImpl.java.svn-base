package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IShopDAO;
import com.shebuyit.po.Shop;
import com.shebuyit.service.IShopService;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ShopServiceImpl implements IShopService {
	private IShopDAO shopDAO;


	public void setShopDAO(IShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public Shop get(String id) {
		return shopDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Shop shop) {
		shopDAO.save(shop);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Shop shop) {
		shopDAO.update(shop);
	}
	
	@Transactional
	public void remove(String id) {
		shopDAO.remove(id);
	}

	public List<Shop> queryShopByQueryCondition(Shop shop,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.shopDAO.queryShopByQueryCondition(shop, orderProperty, isAsc, paging);
	}

	public List<Shop> queryShop(Shop shop) {
		return this.shopDAO.queryShop(shop);
	}
}