package com.shebuyit.dao;

import java.util.List;

import com.shebuyit.common.dao.GenericDAO;
import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Translation;

public interface ITranslationDAO extends GenericDAO<Translation, String> {
	
	Translation get(String id);
	
	void save(Translation transtlation);
	
	List<Translation> queryTranslationByQueryCondition(Translation transtlation, String orderProperty, boolean isAsc, Paging paging);
	
	List<Translation> queryTranslation(Translation transtlation);

}