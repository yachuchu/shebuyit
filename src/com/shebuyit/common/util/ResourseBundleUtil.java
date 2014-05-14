/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================ */

package com.shebuyit.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourseBundleUtil {
	private static ResourceBundle resourceBundle;

	public ResourseBundleUtil(String resourseName, Locale language) {
		resourceBundle = ResourceBundle.getBundle(resourseName, language);
	}

	public ResourseBundleUtil(String resourseName) {
		resourceBundle = ResourceBundle.getBundle(resourseName);
	}

	public Map getAreaResourse() {
		Map<String, String> areaMap = new HashMap<String, String>();
		Enumeration<String> keys = resourceBundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = resourceBundle.getString(key);
			areaMap.put(key, value);
		}
		return areaMap;
	}

	public String getResourceByKey(String key) {
		if (key != null && !key.equals("")) {
			return resourceBundle.getString(key);
		}
		return "";

	}

}
