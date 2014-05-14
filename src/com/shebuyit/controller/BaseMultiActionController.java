/*==============================================================
 * IBM Confidential OCO Source Material                           *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * The source code for this program is not published or otherwise *
 * divested of its trade secrets, irrespective of what has        *
 * been deposited with the U.S. Copyright Office.                 *
 * ============================================================   */

package com.shebuyit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ibm.crl.json4j.config.DateFormatCalculator;
import com.ibm.crl.json4j.config.EnumFormatCalculator;
import com.ibm.crl.json4j.config.JSONSetting;
import com.ibm.crl.json4j.config.Util;
import com.ibm.crl.json4j.util.JsonUtilities;
import com.ibm.json.java.JSONArray;

public class BaseMultiActionController extends MultiActionController {

	public static final String DOJO_GRID_VO_REF = "dojoGridVO";

	protected String returnAjaxResponse(HttpServletRequest request,
			HttpServletResponse response, Object result, String properties) {

		String resultStr = "";
		PrintWriter out = null;
		try {
			resultStr = ObjectToJson(request.getLocale(), result, properties);
			logger.debug("response: " + resultStr);
		} catch (Exception e) {
			logger.error("Fail to convert to JSON String, class: "
					+ result.getClass().getSimpleName());
		}
		try {
			out = response.getWriter();
			out.println(resultStr);
		} catch (IOException ex1) {
			logger.error("Converting to Json throws exception:"
					+ ex1.getMessage(), ex1);
		} finally {
			out.close();
		}
		return null;

	}

	protected String ObjectToJson(Locale locale, Object obj, String properties) {
		String result;
		JSONSetting jsonSetting = new JSONSetting();
		jsonSetting.addJsonPropertyFormatingCalculator(Util.JAVA_UTIL_DATE,
				new DateFormatCalculator(Util.DATEFORMAT_MEDIUM));
		try {
			jsonSetting.addJsonPropertyFormatingCalculator(Util.JAVA_ENUM,
					new EnumFormatCalculator(locale, getApplicationContext()));
			if (properties != null)
				jsonSetting.setFilterStr(properties);
			if (JsonUtilities.isArray(obj)) {
				JSONArray array = JsonUtilities.toJSONArray(obj, jsonSetting);
				result = array.toString();
			} else
				result = JsonUtilities.toJsonObject(obj, jsonSetting)
						.toString();
			logger.debug(result);
		} catch (Exception e) {
			result = "ExeptionWhenConverting";
			logger.error("Converting to Json throws exception:"
					+ e.getMessage(), e);
		}
		return result;
	}

	/**
	 * init binder call back function.
	 * Alow Integer,Double,Long is Null.
	 * 
	 * @see MultiActionController#createBinder(HttpServletRequest,Object)
	 */
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
	}

	protected BindingResult bindObject(HttpServletRequest request,
			Object command) throws Exception {
		Assert.notNull(command);

		ServletRequestDataBinder binder = createBinder(request, command);

		preBind(request, command, binder);

		binder.bind(request);

		Validator[] validators = getValidators();
		if (validators != null) {
			Validator[] arr$ = validators;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; ++i$) {
				Validator validator = arr$[i$];
				if (validator.supports(command.getClass()))
					ValidationUtils.invokeValidator(validator, command, binder
							.getBindingResult());
			}
		}

		return binder.getBindingResult();
	}

	protected void preBind(HttpServletRequest request, Object object,
			ServletRequestDataBinder binder) throws Exception {
	}

}
