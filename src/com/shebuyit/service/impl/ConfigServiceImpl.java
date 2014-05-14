package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IConfigDAO;
import com.shebuyit.po.Config;
import com.shebuyit.service.IConfigService;

public class ConfigServiceImpl implements IConfigService {
	private IConfigDAO configDAO;

	public void setConfigDAO(IConfigDAO configDAO) {
		this.configDAO = configDAO;
	}
	
	public Config get(String id) {
		return configDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Config config) {
		configDAO.save(config);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Config config) {
		configDAO.update(config);
	}
	
	@Transactional
	public void remove(String id) {
		configDAO.remove(id);
	}

	public List<Config> queryConfigByQueryCondition(Config config,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.configDAO.queryConfigByQueryCondition(config, orderProperty, isAsc, paging);
	}

	public List<Config> queryConfig(Config config) {
		return this.configDAO.queryConfig(config);
	}

}
