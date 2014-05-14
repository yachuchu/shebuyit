package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.IDictDAO;
import com.shebuyit.po.Dict;
import com.shebuyit.service.IDictService;

public class DictServiceImpl implements IDictService {
	private IDictDAO dictDAO;

	public void setDictDAO(IDictDAO dictDAO) {
		this.dictDAO = dictDAO;
	}
	
	public Dict get(String id) {
		return dictDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Dict dict) {
		dictDAO.save(dict);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Dict dict) {
		dictDAO.update(dict);
	}
	
	@Transactional
	public void remove(String id) {
		dictDAO.remove(id);
	}

	public List<Dict> queryDictByQueryCondition(Dict dict,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.dictDAO.queryDictByQueryCondition(dict, orderProperty, isAsc, paging);
	}

	public List<Dict> queryDict(Dict dict) {
		return this.dictDAO.queryDict(dict);
	}

}

