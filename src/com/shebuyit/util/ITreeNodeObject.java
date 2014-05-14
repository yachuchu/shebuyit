/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */

package com.shebuyit.util;

import java.util.List;

public interface ITreeNodeObject {


	public final static Integer ROOT_LEVEL = new Integer(0);
	public final static Long ROOT_ID = new Long(0);
	
	public Long getID();
	
	public void setID(Long id);
	
	public String getName();	
	
	public void setName(String name);
	
	public Long getParentID();
	
	public void setParentID(Long id);
	
	public Integer getLevel();
	
	public void setLevel(Integer level);
	
	public List getMyChildren();
	
	public List getAllChildren();
	
	public void setParent(ITreeNodeObject parent);

	public ITreeNodeObject getParent();

	public void addChild(ITreeNodeObject child);	
	
	public Object getObject();
	
	public void setObject(Object obj);
	
	
}
