/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */

package com.shebuyit.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNodeObject implements ITreeNodeObject{

	Object  entityNode;
	
	List children = new LinkedList();
	ITreeNodeObject parent ;
	
	private Long ID;
	
	private Long parentID;
	
	private Integer level; 
	
	private String name = null;
	
	
	public TreeNodeObject() {
	}
	
	public TreeNodeObject(Object entityNode) {
		this.entityNode = entityNode;
	}

	public List getMyChildren() {
		return children;
	}

 
	public List getAllChildren() {
		List allChildren = new LinkedList();
		for(Iterator it = children.iterator();it.hasNext();){
			ITreeNodeObject treenode = (ITreeNodeObject)it.next();
			allChildren.add(treenode);
			allChildren.addAll(treenode.getAllChildren());
		}
		return allChildren;
		
	}

	public ITreeNodeObject getParent() {
		return parent;
	}

	public void setParent(ITreeNodeObject parent) {
		this.parent = parent;
	}

	public void addChild(ITreeNodeObject child) {
		children.add(child);
	}

	public Long getID() {
		return ID;
	}

	public Long getParentID() {
		return parentID;
	}

	public Integer getLevel() {
		return level;
	}

	public void setID(Long id) {
		ID = id;
	}

	public void setParentID(Long id) {
		parentID = id;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		
		this.name = name; 
	}

    public Object getObject() {
        return entityNode;
    }

    public void setObject(Object obj) {
        entityNode= obj;
    }

}
