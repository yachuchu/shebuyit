package com.shebuyit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.ITranslationDAO;
import com.shebuyit.po.Translation;
import com.shebuyit.service.ITranslationService;

public class TranslationServiceImpl implements ITranslationService {
	private ITranslationDAO translationDAO;

	public void setTranslationDAO(ITranslationDAO translationDAO) {
		this.translationDAO = translationDAO;
	}
	
	public Translation get(String id) {
		return translationDAO.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Translation translation) {
		translationDAO.save(translation);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Translation translation) {
		translationDAO.update(translation);
	}
	
	@Transactional
	public void remove(String id) {
		translationDAO.remove(id);
	}

	public List<Translation> queryTranslationByQueryCondition(Translation translation,String orderProperty, boolean isAsc,
			Paging paging) {

		return this.translationDAO.queryTranslationByQueryCondition(translation, orderProperty, isAsc, paging);
	}

	public List<Translation> queryTranslation(Translation translation) {
		return this.translationDAO.queryTranslation(translation);
	}

}

