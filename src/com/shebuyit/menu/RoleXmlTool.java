package com.shebuyit.menu;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RoleXmlTool {
	private NodeList noList;

	private Node[] roleNodes;

	private String[] roles;

	private List<String> targetRoleList = new ArrayList<String>();

	private boolean errorOccur = false;

	public void initFile(String fileName) throws Exception {

		try {			
			URL url = null;
			try {
				url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			} catch (RuntimeException e) {			
			}
			if(url==null){
				try {
					url=RoleXmlTool.class.getClassLoader().getResource(fileName);
				} catch (RuntimeException e) {				
				}
			}
			if(url==null)
				throw new RuntimeException("Can't get the file: "+fileName);
			
			File file = new File(url.getFile());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			noList = doc.getElementsByTagName("role");								
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void initRole(String[] roles) {
		if(null == roles || 0 == roles.length){
			errorOccur = true;
			return;
		}
		this.roles = roles;
		roleNodes = new Node[roles.length];
		initRoleNode();
		initTargetRole();
		try {
			listOtherRole();
		} catch (TransformerException e) {
			e.printStackTrace();
			errorOccur = true;
		}
	}

	public String getXml() {
		String reValue = "";
		try {
			if (errorOccur) {
				return "";
			}
			reValue = node2String(roleNodes[0]);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			errorOccur = true;
		} catch (TransformerException e) {
			e.printStackTrace();
			errorOccur = true;
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			errorOccur = true;
		}
		return reValue;
	}

	private void initRoleNode() {
		String roleId;
		int count = 0;

		for (int i = 0; i < noList.getLength(); i++) {
			roleId = noList.item(i).getAttributes().getNamedItem("id")
					.getNodeValue();
			if (checkRole(roleId)) {
				roleNodes[count] = noList.item(i);
				count++;
			}
		}
	}

	private boolean checkRole(String roleId) {
		for (int i = 0; i < roles.length; i++) {
			if (roleId.equals(roles[i])) {
				return true;
			}
		}
		return false;
	}

	private void initTargetRole() {
		targetRoleList = getFunctionId(roleNodes[0]);
	}

	private void listOtherRole() throws TransformerException {
		for (int i = roleNodes.length - 1; i > 0; i--) {
			getFunctionId(roleNodes[i]);
			getFunctionContent(roleNodes[i]);
		}
	}

	private List<String> getFunctionId(Node function) {
		String name;
		int type;
		NodeList functionNodes;
		NodeList subMenuNodes;
		functionNodes = function.getChildNodes();

		List<String> reList = new ArrayList<String>();
		for (int j = 0; j < functionNodes.getLength(); j++) {
			type = functionNodes.item(j).getNodeType();
			if (1 == type) {
				name = functionNodes.item(j).getNodeName();
				if ("function".equals(name)) {
					subMenuNodes = functionNodes.item(j).getChildNodes();
					for (int j2 = 0; j2 < subMenuNodes.getLength(); j2++) {
						type = subMenuNodes.item(j2).getNodeType();
						if (1 == type) {
							name = subMenuNodes.item(j2).getNodeName();
							if ("id".equals(name)) {
								reList.add(subMenuNodes.item(j2)
										.getTextContent());
							}
						}
					}
				}
			}
		}
		return reList;
	}

	private void getFunctionContent(Node function)
			throws TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError {
		String name;
		int type;
		NodeList functionNodes;
		NodeList subMenuNodes;

		functionNodes = function.getChildNodes();
		String functionId;
		String tempFunctionId;

		Node root = roleNodes[0];
		Element functionElement;
		Element appendChild;

		for (int j = 0; j < functionNodes.getLength(); j++) {
			type = functionNodes.item(j).getNodeType();
			if (1 == type) {
				name = functionNodes.item(j).getNodeName();
				if ("function".equals(name)) {
					subMenuNodes = functionNodes.item(j).getChildNodes();
					for (int j2 = 0; j2 < subMenuNodes.getLength(); j2++) {
						type = subMenuNodes.item(j2).getNodeType();
						if (1 == type) {
							name = subMenuNodes.item(j2).getNodeName();
							if ("id".equals(name)) {
								appendChild = (Element) functionNodes.item(j);
								functionId = subMenuNodes.item(j2)
										.getTextContent();
								if (!roleExist(functionId)) {
									targetRoleList.add(functionId);
									root.appendChild(appendChild);
								} else {
									for (int i = 0; i < ((Element) root)
											.getElementsByTagName("id")
											.getLength(); i++) {
										tempFunctionId = ((Element) root)
												.getElementsByTagName("id")
												.item(i).getTextContent();
										if (functionId.equals(tempFunctionId)) {
											functionElement = (Element) ((Element) root)
													.getElementsByTagName("id")
													.item(i).getParentNode();
											
											composeMenu(functionElement,
													(Element) functionNodes
															.item(j));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void composeMenu(Element target, Element op)
			throws TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError {
		List<Element> targetMenus = getSubMenu(target);
		List<Element> opMenus = getSubMenu(op);

		Element targetMenu;
		Element opMenu;

		String targetMenuId;
		String opMenuId;

		boolean appendFlag = true;

		for (int i = 0; i < opMenus.size(); i++) {
			opMenu = opMenus.get(i);
			opMenuId = getSubMenuId(opMenu);
			appendFlag = true;
			for (int j = 0; j < targetMenus.size(); j++) {

				targetMenu = targetMenus.get(j);
				targetMenuId = getSubMenuId(targetMenu);
				if (opMenuId.equals(targetMenuId)) {
					composeSubMenu(targetMenu, opMenu);
					appendFlag = false;
					break;
				}
			}

			if (appendFlag) {
				// composeSubMenu(targetMenus.get(i), opMenu);
				if (targetMenus != null && targetMenus.size() != 0)
					targetMenus.get(0).getParentNode().appendChild(opMenu);
			}
		}
	}

	private void composeSubMenu(Element target, Element op) {

		String targetId = getSubMenuId(target);
		String opId = getSubMenuId(op);

		List<Element> targetSubMenu;
		List<Element> opSubMenu;

		String targetSubMenuId;
		String opSubMenuId;

		boolean appendFlag = true;

		if (targetId.equals(opId)) {
			targetSubMenu = getSubMenu(target);
			opSubMenu = getSubMenu(op);

			if (0 == targetSubMenu.size() && 0 < opSubMenu.size()) {
				for (int i = 0; i < opSubMenu.size(); i++) {
					target.appendChild(opSubMenu.get(i));
				}
			} else if (0 < targetSubMenu.size() && 0 < opSubMenu.size()) {
				for (int i = 0; i < opSubMenu.size(); i++) {
					appendFlag = true;
					opSubMenuId = getSubMenuId(opSubMenu.get(i));
					for (int j = 0; j < targetSubMenu.size(); j++) {
						targetSubMenuId = getSubMenuId(targetSubMenu.get(j));
						if (targetSubMenuId.equals(opSubMenuId)) {
							appendFlag = false;
							composeSubMenu(targetSubMenu.get(j), opSubMenu
									.get(i));
							break;
						}
					}

					if (appendFlag) {
						targetSubMenu.get(0).getParentNode().appendChild(
								opSubMenu.get(i));
					}
				}
			}
		}
	}

	private String getSubMenuId(Element menu) {
		NodeList nl = menu.getChildNodes();
		String name;
		String reValue = "";
		for (int i = 0; i < nl.getLength(); i++) {
			if (1 == nl.item(i).getNodeType()) {
				name = nl.item(i).getNodeName();
				if ("id".equals(name)) {
					reValue = nl.item(i).getTextContent();
				}
			}
		}
		return reValue;
	}

	private List<Element> getSubMenu(Element menu) {
		List<Element> reValue = new ArrayList<Element>();

		NodeList nl = menu.getChildNodes();
		String name;
		for (int i = 0; i < nl.getLength(); i++) {
			if (1 == nl.item(i).getNodeType()) {
				name = nl.item(i).getNodeName();
				if ("subMenu".equals(name)) {
					reValue.add((Element) nl.item(i));
				}
			}
		}
		return reValue;
	}

	private boolean roleExist(String roleId) {
		for (int i = 0; i < targetRoleList.size(); i++) {
			if (roleId.equals(targetRoleList.get(i))) {
				return true;
			}
		}
		return false;
	}

	private String node2String(Node node)
			throws TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError {
		StringWriter output = new StringWriter();
		TransformerFactory.newInstance().newTransformer().transform(
				new DOMSource(node), new StreamResult(output));
		String xmlFile = output.toString();
		return xmlFile;
	}

}
