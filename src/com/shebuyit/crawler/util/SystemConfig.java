package com.shebuyit.crawler.util;

import java.io.File;

public class SystemConfig {
	
	public static String site = "shechic";
	
//	public static String site = "21chic";
	
//	public static String site = "shebuysmart";
	
	
	public static String site_csv = " on sale at www.shechic.com";
	
//	public static String site_csv = " on sale at www.21chic.com";
	
//	public static String site_csv = " on sale at www.shebuysmart.com";
	

	public static String site_sku = "";
	
//	public static String site_sku = "";
	
//	public static String site_sku = "SBS";
	
	//constant values
    //图片存储路径
	//windows
	//public static String Image_Path = "E:/sheshining1/images/";

    //csv文件存储路径
   //windows
    //public static String File_Path = "E:/shebuysmart/"; 
    
    
    //美元汇率
   //public static double Dollar_rate = 6.2; 
    
    public static double Profit = 100; 

 
	public static String File_Path() {
			
		StringBuffer sb = new StringBuffer();
		
		sb.append("E:/shebuysmart/");
		
		//sb.append(File.separator).append("home").append(File.separator).append("shebuysmart").append(File.separator);
		
		return sb.toString();
	}
    
	public static String Image_Path() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("E:/shebuysmart1/images/");
		
		//sb.append(File.separator).append("home").append(File.separator).append("shebuysmart").append("images").append(File.separator);
		
		return sb.toString();
	}
    
    
    
}
