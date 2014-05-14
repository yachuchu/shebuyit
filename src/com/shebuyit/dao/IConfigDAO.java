package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Config;

public interface IConfigDAO extends GenericDAO<Config, String> {
	
	Config get(String id);
	
	void save(Config shop);
	
	List<Config> queryConfigByQueryCondition(Config config, String orderProperty, boolean isAsc, Paging paging);
	
	List<Config> queryConfig(Config config);


}
