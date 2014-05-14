
package com.shebuyit.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IProductDAO;
import com.shebuyit.po.Product;

/**
 * @author chuyazhou
 *
 */
public class ProductDAOImpl extends JPAGenericDAOImpl<Product, String>
		implements IProductDAO {

	public List<Product> queryProductByQueryCondition(Product product, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(product);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<Product> queryProduct(Product product) {
		String condition = getQueryCondition(product);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(Product product) {
		String condition = " 1=1 ";
		Integer stock = product.getStock();
		if (stock!=null) {
			condition += " and obj.stock =" +stock+ " ";			
		}
		String sku = product.getSku();
		if (StringUtils.isNotEmpty(sku)) {
			//condition += " and obj.sku ='" +sku+ "'";
			condition += " and LOWER(obj.sku) LIKE '%" + StringEscapeUtils.escapeSql(sku).toLowerCase() + "%'";
		}
		
		String productSku = product.getProductSku();
		if (StringUtils.isNotEmpty(productSku)) {
			condition += " and obj.productSku ='" +productSku+ "'";			
		}
		
		String shopSku = product.getShopSku();
		if (StringUtils.isNotEmpty(shopSku)) {
			condition += " and obj.shopSku ='" +shopSku+ "'";			
		}
		String category = product.getCategory();
		if (StringUtils.isNotEmpty(category)) {
			condition += " and obj.category ='" +category+ "'";			
		}
		String englishName = product.getEnglishName();
		if (StringUtils.isNotEmpty(englishName)) {
			condition += " and LOWER(obj.englishName) LIKE '%" + StringEscapeUtils.escapeSql(englishName).toLowerCase() + "%'";
		}
		
		Integer isFreeSize = product.getIsFreeSize();
		if (isFreeSize!=null) {
			condition += " and obj.isFreeSize =" +isFreeSize+ " ";			
		}
		
		Integer isExport = product.getIsExport();
		if (isExport!=null) {
			condition += " and obj.isExport =" +isExport+ " ";			
		}
		
		String shopCanal = product.getShopCanal();
		if (StringUtils.isNotEmpty(shopCanal)) {
			condition += " and obj.shopCanal ='" +shopCanal+ "'";			
		}
		
//		String description = product.getDescription();
//		if (StringUtils.isNotEmpty(description)) {
//			condition += " and LOWER(obj.description) LIKE '%One Size%' or LOWER(obj.description) LIKE '%Free Size%'";
//		}

		String time_start = product.getTime_start();
		String time_end = product.getTime_end();
		if (StringUtils.isNotEmpty(time_start)&&StringUtils.isNotEmpty(time_end)) {
			if(time_start.contains("-")&&time_end.contains("-")){
				condition += " and obj.created_time between '"+time_start+"' and '"+time_end+"' ";
			}else{
				condition += " and obj.id between "+time_start+" and "+time_end+" ";
			}				
		}else if(StringUtils.isNotEmpty(time_start)&&!StringUtils.isNotEmpty(time_end)){
			
			if(time_start.contains("-")){
				condition += " and obj.created_time > '"+time_start+"' ";
			}else{
				condition += " and obj.id > "+time_start+" ";
			}
		}else if(!StringUtils.isNotEmpty(time_start)&&StringUtils.isNotEmpty(time_end)){
			if(time_end.contains("-")){
				condition += " and obj.created_time < '"+time_end+"' ";
			}else{
				condition += " and obj.id < "+time_end+" ";
			}
			
		}
							
		return condition;
	}

	public Product get(String id) {
		return super.get(id);
	}

	public void save(Product product) {
		super.save(product);
	}
}
