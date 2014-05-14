package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Config;

public interface IConfigService {

	void save(Config config);

	Config get(String id);

	void update(Config config);

	void remove(String id);

	List<Config> queryConfigByQueryCondition(Config config, String orderProperty,boolean isAsc, Paging paging);

	List<Config> queryConfig(Config config);
}