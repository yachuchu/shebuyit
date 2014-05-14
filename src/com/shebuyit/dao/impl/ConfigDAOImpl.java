package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IConfigDAO;
import com.shebuyit.po.Config;

public class ConfigDAOImpl extends JPAGenericDAOImpl<Config, String>
		implements IConfigDAO {

	public List<Config> queryConfigByQueryCondition(Config config, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(config);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<Config> queryConfig(Config config) {
		String condition = getQueryCondition(config);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(Config config) {
		String condition = " 1=1 ";
		String configName = config.getConfigName();
		if (StringUtils.isNotEmpty(configName)) {
			condition += " and obj.configName ='" +configName+ "'";			
		}
		String configValue = config.getConfigValue();
		if (StringUtils.isNotEmpty(configValue)) {
			condition += " and obj.configValue ='" +configValue+ "'";			
		}		
		return condition;
	}

	public Config get(String id) {
		return super.get(id);
	}

	public void save(Config config) {
		super.save(config);
	}

	
}
