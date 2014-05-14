package com.shebuyit.service;

import java.util.List;

import com.shebuyit.common.util.Paging;
import com.shebuyit.po.Translation;

public interface ITranslationService {
	void save(Translation translation);

	Translation get(String id);

	void update(Translation translation);

	void remove(String id);

	List<Translation> queryTranslationByQueryCondition(Translation translation, String orderProperty,boolean isAsc, Paging paging);

	List<Translation> queryTranslation(Translation translation);
}
