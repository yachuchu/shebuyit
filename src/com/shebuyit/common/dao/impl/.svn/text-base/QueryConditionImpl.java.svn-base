/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */

package com.shebuyit.common.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.shebuyit.common.dao.IQueryCondition;

public class QueryConditionImpl implements IQueryCondition, Serializable{
	
	private static final long serialVersionUID = -3748820939432948050L;
	
	private Logger logger = Logger.getLogger(QueryConditionImpl.class.getName());

	@SuppressWarnings("unchecked")
	Map queryData = new HashMap();
	
	Long pageSize = new Long(10);
		
	Long pageIndex = new Long(1);
	
	Boolean isPageQuery;

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	
	public Boolean isPageQuery() {
		return new Boolean(pageSize != null && pageIndex != null);
	}

	public String getQueryCondition(String conditionName) {
		String ret = null;
		try {
			ret = (String) queryData.get(conditionName);
		} catch(Exception ex) {
			ret = "";
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public Map getAllQueryConditions() {
		return queryData;
	}
	
	@SuppressWarnings("unchecked")
	public void addQueryCondition(String conditionName, String conditionValue) {
		if(conditionValue!=null) {
			//trim the space
			conditionValue = conditionValue.trim();
			//replace the ''
			conditionValue = conditionValue.replaceAll("'","''");
		}
		queryData.put(conditionName, conditionValue);
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public Long getPageIndex() {
		return this.pageIndex;
	}

	@SuppressWarnings("unchecked")
	public void addQueryConditionByListValue(String conditionName, List conditionValue) {
		queryData.put(conditionName, conditionValue);		
	}

	@SuppressWarnings("unchecked")
	public List getQueryConditionByListValue(String conditionName) {
		List ret = new ArrayList();
		if(conditionName != null) {
			try {
				ret = (List) queryData.get(conditionName);
			} catch(Exception ex) {
			    logger.debug("[QueryCondition Err]: " + conditionName + ", expecting value: java.lang.List");
			}
		}
		return ret;
	}

	
    @SuppressWarnings("unchecked")
	public void addQueryConditionByMapValue(String conditionName, Map conditionValue) {
        queryData.put(conditionName, conditionValue);        
    }

    @SuppressWarnings("unchecked")
	public Map getQueryConditionByMapValue(String conditionName) {
        Map ret = new HashMap();
		if(conditionName != null) {
			try {
				ret = (Map) queryData.get(conditionName);
			} catch(Exception ex) {
				logger.debug("[QueryCondition Err]: " + conditionName + ", expecting value: java.lang.Map");
			}
		}
		return ret;
    }
	
	
}
