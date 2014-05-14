package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IDictDAO;
import com.shebuyit.po.Dict;

public class DictDAOImpl extends JPAGenericDAOImpl<Dict, String>
		implements IDictDAO {

	public List<Dict> queryDictByQueryCondition(Dict dict, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(dict);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<Dict> queryDict(Dict dict) {
		String condition = getQueryCondition(dict);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(Dict Dict) {
		String condition = " 1=1 ";
		String name = Dict.getName();
		if (StringUtils.isNotEmpty(name)) {
			condition += " and obj.name ='" +name+ "'";			
		}
		String code = Dict.getCode();
		if (StringUtils.isNotEmpty(code)) {
			condition += " and obj.code ='" +code+ "'";			
		}		
		String timeStart = Dict.getTime_start();
		String timeEnd = Dict.getTime_end();
		if (StringUtils.isNotEmpty(timeStart)&&StringUtils.isNotEmpty(timeEnd)) {
			condition += " and obj.created_time between '" +timeStart+ "' and '" +timeEnd+ "'";			
		}
		if (StringUtils.isNotEmpty(timeStart)&&StringUtils.isEmpty(timeEnd)) {
			condition += " and obj.created_time >='" +timeStart+ "'";			
		}
		if (StringUtils.isEmpty(timeStart)&&StringUtils.isNotEmpty(timeEnd)) {
			condition += " and obj.code <='" +timeEnd+ "'";			
		}
		
		return condition;
	}

	public Dict get(String id) {
		return super.get(id);
	}

	public void save(Dict dict) {
		super.save(dict);
	}

}
