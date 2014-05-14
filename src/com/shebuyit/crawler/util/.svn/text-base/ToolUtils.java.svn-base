package com.shebuyit.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolUtils {
	
	public static void main(String[] args) {
		String ss= "ssdsfs111s";
		System.out.println(ToolUtils.hasNumeric(ss));
	}
	

	public static boolean hasNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher  = pattern.matcher(str);//判断是否含有数字   
		return matcher.find(0);
	}

}
