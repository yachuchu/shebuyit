package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Dict;

public interface IDictDAO extends GenericDAO<Dict, String> {
	
	Dict get(String id);
	
	void save(Dict dict);
	
	List<Dict> queryDictByQueryCondition(Dict dict, String orderProperty, boolean isAsc, Paging paging);
	
	List<Dict> queryDict(Dict dict);

}