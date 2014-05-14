package com.shebuyit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shebuyit.common.dao.impl.JPAGenericDAOImpl;
import com.shebuyit.common.util.Paging;
import com.shebuyit.dao.ITranslationDAO;
import com.shebuyit.po.Translation;

public class TranslationDAOImpl extends JPAGenericDAOImpl<Translation, String>
		implements ITranslationDAO {

	public List<Translation> queryTranslationByQueryCondition(Translation translation, String orderProperty, boolean isAsc,Paging paging) {
		String condition = getQueryCondition(translation);
		String queryString = "select obj from " + entityClass.getSimpleName() + " obj where";
		queryString += condition;
		String ascStr = isAsc ? "asc" : "desc";
		queryString +=" order by obj." + orderProperty + " " + ascStr;
		return super.find(queryString, paging);

	}
	
	public List<Translation> queryTranslation(Translation translation) {
		String condition = getQueryCondition(translation);
		String queryString = "select obj from " + entityClass.getSimpleName()+ " obj where";
		queryString += condition;
		return super.find(queryString);
	}
	
	private String getQueryCondition(Translation translation) {
		String condition = " 1=1 ";
		String zhName = translation.getZhName();
		if (StringUtils.isNotEmpty(zhName)) {
			condition += " and obj.zhName ='" +zhName+ "'";			
		}
		String enName = translation.getEnName();
		if (StringUtils.isNotEmpty(enName)) {
			condition += " and obj.enName ='" +enName+ "'";			
		}		
		String timeStart = translation.getTime_start();
		String timeEnd = translation.getTime_end();
		if (StringUtils.isNotEmpty(timeStart)&&StringUtils.isNotEmpty(timeEnd)) {
			condition += " and obj.created_time between '" +timeStart+ "' and '" +timeEnd+ "'";			
		}
		if (StringUtils.isNotEmpty(timeStart)&&StringUtils.isEmpty(timeEnd)) {
			condition += " and obj.created_time >='" +timeStart+ "'";			
		}
		if (StringUtils.isEmpty(timeStart)&&StringUtils.isNotEmpty(timeEnd)) {
			condition += " and obj.code <='" +timeEnd+ "'";			
		}
		
		return condition;
	}

	public Translation get(String id) {
		return super.get(id);
	}

	public void save(Translation Translation) {
		super.save(Translation);
	}

}
