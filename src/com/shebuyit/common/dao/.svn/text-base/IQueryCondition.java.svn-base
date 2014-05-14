/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */

package com.shebuyit.common.dao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IQueryCondition extends Serializable{
		
	
	public void addQueryCondition(String conditionName,String conditionValue);

   	@SuppressWarnings("unchecked")
	public void addQueryConditionByListValue(String conditionName, List conditionValue);

	@SuppressWarnings("unchecked")
	public void addQueryConditionByMapValue(String conditionName, Map conditionValue);
	
	public String getQueryCondition(String conditionName);
	
	@SuppressWarnings("unchecked")
	public List getQueryConditionByListValue(String conditionName);
	
	@SuppressWarnings("unchecked")
	public Map getQueryConditionByMapValue(String conditionName);
	
	public Long getPageSize();

	public void setPageSize(Long pageSize);
	
	public void setPageIndex(Long pageIndex);
	
	public Long getPageIndex();
	
	public Boolean isPageQuery();
	
	@SuppressWarnings("unchecked")
	public Map getAllQueryConditions();

}
