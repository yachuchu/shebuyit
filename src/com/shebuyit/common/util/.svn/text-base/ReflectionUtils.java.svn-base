/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================ */
package com.shebuyit.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

	public static List<Class> getSuperClassGenricTypes(Class clazz) {
		List<Class> typeList = new ArrayList<Class>();
		Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
		if (types != null) {
			for (Type type : types) {
				typeList.add((Class) type);
			}
		}
		return typeList;
	}

}
