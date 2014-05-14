
package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IProductDAO;
import com.shebuyit.po.Product;
import com.shebuyit.service.IProductService;

/**
 * @author chuyazhou
 * 
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements IProductService {
	private IProductDAO productDAO;


	public void setProductDAO(IProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Transactional
	public Product get(String id) {
		return productDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Product product) {
		productDAO.save(product);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Product product) {
		productDAO.update(product);
	}
	
	@Transactional
	public void remove(String id) {
		productDAO.remove(id);
	}
	
	@Transactional
	public void remove(Product product) {
		productDAO.remove(product);
	}

	public List<Product> queryProductByQueryCondition(Product product,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.productDAO.queryProductByQueryCondition(product, orderProperty, isAsc, paging);
	}
	
	public List<Product> queryProduct(Product product) {
		return productDAO.queryProduct(product);
	}
}