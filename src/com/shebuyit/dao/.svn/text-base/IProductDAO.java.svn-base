
package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Product;

/**
 * @author chuyazhou
 *
 */
public interface IProductDAO extends GenericDAO<Product, String> {
	
	Product get(String id);
	
	void save(Product product);
	
	List<Product> queryProductByQueryCondition(Product product, String orderProperty, boolean isAsc, Paging paging);
	
	List<Product> queryProduct(Product product);
}
