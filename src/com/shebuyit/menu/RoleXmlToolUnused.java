/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */

package com.shebuyit.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shebuyit.po.UserPrivilege;
import com.shebuyit.util.ITreeNodeObject;
import com.shebuyit.util.TreeNodeObject;
import com.shebuyit.vo.UserPrivilegeVO;

public class RoleXmlToolUnused {

    public String getXmlStreamFromAllPrivilege() {

	    //get privileges from database
//	    List accessList =  getUserPrivilegeByUserID();
//	    //transform to TreeNode
//	    List treeList = buildTreeNodeFromUserPrivilege(accessList);
//	    //
//	    ITreeNodeObject treeRoot = getTreeFromOrderList(treeList);
//	    //generate xml node
//	    UserPrivilegeVO rootPri = tranversalForUserPrivilege(treeRoot);
    	
    	UserPrivilegeVO rootPri = null;
	    
	    //generate xml stream
	    return  getXMLStreamFromRoot(rootPri);
    }
    
    private List getUserPrivilegeByUserID(){
    	List temp = new ArrayList<UserPrivilege>();
    	UserPrivilege up1 = new UserPrivilege();
    	up1.setId(new Long(1));
    	up1.setName("privileg1");
    	up1.setParentID(new Long(0));
    	up1.setUrlPattern("http://");
    	temp.add(up1);
    	
    	return temp;
    }
    
	private List buildTreeNodeFromUserPrivilege(List userPriList){
	    
	    List treeNodes = new ArrayList();
	    for(Iterator it=userPriList.iterator();it.hasNext();){
	    	UserPrivilege up = (UserPrivilege)it.next();
	        ITreeNodeObject treeNode =new TreeNodeObject();
	        treeNode.setObject(up);
	        treeNode.setID(up.getId());
	        treeNode.setLevel(up.getLevel());
	        treeNode.setName(up.getName());
	        treeNode.setParentID(new Long(up.getParentID()));
	        treeNodes.add(treeNode);
	        
	    }
	    return treeNodes;
	}
	
	private UserPrivilegeVO tranversalForUserPrivilege(ITreeNodeObject root){
	    if(root==null){
	        UserPrivilegeVO pit = new UserPrivilegeVO();
	        return pit;
	    }
	    UserPrivilegeVO upVO = new UserPrivilegeVO();
	    UserPrivilege up= (UserPrivilege)root.getObject();
	    upVO.setName(up.getName());
	    upVO.setId(up.getId());
	    upVO.setUrlPattern(up.getUrlPattern());
	    upVO.setLevel(up.getLevel().intValue());

	    List childs=root.getMyChildren();
	    for(Iterator it= childs.iterator();it.hasNext();){
	    	UserPrivilegeVO child = tranversalForUserPrivilege((ITreeNodeObject)it.next());
	        upVO.getPrivileges().add(child);
	    }
	    return upVO;	    
	}
	
	private String getXMLStreamFromRoot(UserPrivilegeVO rootPri){
	    String res ="";

	    //build XML DOM tree from privilege tree
	    
	    //generate XML stream from DOM tree.
	    
//	    ByteArrayOutputStream out = new  ByteArrayOutputStream();
//
////            FileOutputStream fout= new FileOutputStream("c:/sd.xml");
//            try {
//    	        Map options= new HashMap();
//    		    options.put(XMLResource.OPTION_ENCODING ,
//    	                "UTF-8");
//                resource.save(out,options);
//                
//                res = out.toString("UTF-8");
//    			return res;
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }finally{
//    			if(out!=null)
//    				try {
//    					out.close();
//    				} catch (IOException e) {
//    					// TODO Auto-generated catch block
//    					e.printStackTrace();
//    				}
//    		}
	    
	    res = "	<role id=\"Super\"> " + 
	    			"<name>Super</name>" +
	    			"<function>" + 
	    				"<id>Select</id>" +
	    				"<visible>true</visible>" +
	    				"<url>#</url>" +
	    				"<subMenu>" +
    						"<id>Collection</id>" +
    						"<visible>true</visible>" +
    						"<url>/SCATaxWeb/collection/search.tax</url>" +
    					"</subMenu>" +
    					"<subMenu>" +
							"<id>Batch</id>" +
							"<visible>true</visible>" +
							"<url>/SCATaxWeb/batch/search.tax</url>" +
						"</subMenu>" +
						"<subMenu>" +
							"<id>Parcel</id>" +
							"<visible>true</visible>" +
							"<url>/SCATaxWeb/parcel/search.tax</url>" +
						"</subMenu>" +					
	    			"</function>" +	   		
	    			"<function>" + 
    					"<id>Reports</id>" +
    					"<visible>true</visible>" +
    					"<url>#</url>" +
    				"</function>" +
    				"<function>" + 
    					"<id>Utilitites</id>" +
    					"<visible>true</visible>" +
    					"<url>#</url>" +
    				"</function>" +
    				"<function>" + 
    					"<id>Setup</id>" +
    					"<visible>true</visible>" +
    					"<url>#</url>" +
    				"</function>" +
	    		"</role>";

			return res;
	}
	
	private void createXmlNodes(Document xmldoc, Element parent, UserPrivilegeVO privileges){

	}
	
	public  static ITreeNodeObject getTreeFromOrderList(List list)
	{
		Integer rootLevel ;
		ITreeNodeObject nodeObj ;
		//ITreeNodeObject  treeNode;
		HashMap map = new HashMap();
		
		if(list == null || list.size()==0)
			return null;
		boolean hasRoot = false;
		hasRoot = hasRoot(list);
		if(!hasRoot ){
			ITreeNodeObject root = new TreeNodeObject();
			root.setObject(createVirtualRootObj(list));
			List listtmp = new LinkedList();
			listtmp.add(root);
			rootLevel = root.getLevel();
			map.put(root.getLevel(),listtmp);
		}else{
			rootLevel = ((ITreeNodeObject)list.get(0)).getLevel();
		}
		
		for(Iterator it = list.iterator();it.hasNext();) {
			Object obj =  it.next();
			if(!(obj instanceof ITreeNodeObject)) {
				return null;
			}
			nodeObj = (ITreeNodeObject) obj;
			
			if(!findParent(map,nodeObj)){
				System.err.println("[EntityUtil] error in create the tree!");
				return null;
			}
		}
		
		return (ITreeNodeObject)((List)map.get(rootLevel)).get(0);
	}
	
	private static boolean findParent(HashMap map,ITreeNodeObject node){
		if(node == null ||node.getLevel() == null)
			return false;
				
		if(map.keySet().size()==0){			
			List listtmp = new LinkedList();
			listtmp.add(node);
			map.put(node.getLevel(),listtmp);
			return true;
		}
		
		List list = (List)map.get(new Integer(node.getLevel().intValue()-1));
		for(Iterator it = list.iterator();it.hasNext();) {
			
			ITreeNodeObject treeNode = (ITreeNodeObject) it.next();
			
			if(treeNode.getID().equals(node.getParentID())) {
			
				node.setParent(treeNode);
				treeNode.addChild(node);				
				List children = (List)map.get(node.getLevel());
				if(children == null) { 
					children = new LinkedList();	
				}
				children.add(node);
				map.put(node.getLevel(),children);
			
				return true;
			}
		}
		System.err.println("error in " +
				"create the tree:cannot find a parent for current node "+ node.getID()+
				"Level:"+node.getLevel());
		return false;
	}
	
	public static boolean hasRoot(List nodes)
	{
		if(nodes==null)
			return false;

		//
		Integer firstLevel = null;
		ITreeNodeObject entity =null;
		for(Iterator it = nodes.iterator();it.hasNext();){
			try{
				entity = (ITreeNodeObject)it.next();
			}catch(ClassCastException e) {
				e.printStackTrace();
				return false;
			}
			
			if(firstLevel==null) {
				firstLevel = entity.getLevel();//
			}else {
				if(firstLevel.equals(entity.getLevel())){
					return false;
				}else 	{
					return true;
				}
			}
		}
		
		return false;
	} 
	
	private static ITreeNodeObject createVirtualRootObj(List nodes) {
	    ITreeNodeObject firstEntity =(ITreeNodeObject) nodes.get(0);
	    ITreeNodeObject entity = new TreeNodeObject();
		entity.setLevel(new Integer(firstEntity.getLevel().intValue()-1));
		entity.setID(firstEntity.getParentID());
		return entity;
	}

}
