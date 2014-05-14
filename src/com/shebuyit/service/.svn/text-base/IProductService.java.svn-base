
package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Product;

/**
 * @author chuyazhou
 *
 */
public interface IProductService {
	
	void save(Product product);

	Product get(String id);

	void update(Product product);
	
	void remove(String id);
	
	void remove(Product product);

	List<Product> queryProductByQueryCondition(Product product,String orderProperty, boolean isAsc,
			Paging paging);
	
	List<Product> queryProduct(Product product);
}