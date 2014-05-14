package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Dict;

public interface IDictService {
	void save(Dict dict);

	Dict get(String id);

	void update(Dict dict);

	void remove(String id);

	List<Dict> queryDictByQueryCondition(Dict dict, String orderProperty,boolean isAsc, Paging paging);

	List<Dict> queryDict(Dict dict);
}
