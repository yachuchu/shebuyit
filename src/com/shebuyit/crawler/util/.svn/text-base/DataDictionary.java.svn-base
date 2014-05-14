package com.shebuyit.crawler.util;

import java.util.ResourceBundle;

public class DataDictionary {

	public static void main(String[] args) {
		
//		String shopname = "妖精的口袋";s
//		String name = "维多利亚完美修身气质连sdsds衣裙 宴会裙 2色6码全 包邮";
//		name = name.replaceAll(shopname, " ");
//		name = trimProductName(name);
////		name = translateEnToCinese(name);
//		System.out.println(name);
		String aicss = stringToAsc("3XL 4XL");
		System.out.println("aicss---"+aicss);
		System.out.println("aicss---3 4");
		
//		String str = trimProductName(name);
//		System.out.println("str---"+str);

		//String test = getTypesValue(str);
		//System.out.println(test);

	}

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("system");

	public static String getValue(String key) {
		
		String keyAsc = stringToAsc(key);		
		String value = null;
		try{
		 value = resourceBundle.getString(keyAsc);//
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("-------error---key--------"+key);
		}	
		return value;
	}


	public static String ascToString(String str) {//

		String[] chars = str.split(";");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			sb.append((char) Integer.parseInt(chars[i].replace("&#", "")));
		}
		return sb.toString();
	}

	public static String stringToAsc(String str) {// 


		char[] chars = str.toCharArray(); //

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {// 

			sb.append("&#").append((int)chars[i]).append(";");
		}
		return sb.toString();
	}
	
	
	public static String getSizeValue(String key) {//
		String keyStr = "";
		if(key.contains("均码")||key.contains("一")||key.contains("单")){
			keyStr = "均码";
		}else if(key.contains("XS")){
			keyStr = "XS";
		}else if(key.contains("S")){
			keyStr = "S";
		}else if(key.contains("M")){
			keyStr = "M";
		}else if(key.contains("XXXXL")||key.contains("4X")||key.contains("4L")){
			keyStr = "4XL";
		}else if(key.contains("XXXL")||key.contains("3X")){
			keyStr = "3XL";
		}else if(key.contains("XXL")){
			keyStr = "XXL";
		}else if(key.contains("XL")){
			keyStr = "XL";
		}else if(key.contains("L")){
			keyStr = "L";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getPantsSizeValue(String key) {//		
		String value = "";
		if(key.contains("码")){
			 value = key.substring(0, key.indexOf("码"));
		}
		//String value = key.replaceAll("[\\u4e00-\\u9fa5]", "");
		
		return value;
	}
	
	public static String getColorValue(String key) {//
		String keyStr = "";
		if(key.contains("红")){
			keyStr = "红";
		}else if(key.contains("黑")){
			keyStr = "黑";
		}else if(key.contains("绿")||key.contains("薄荷")||key.contains("青")||key.contains("植物")){
			keyStr = "绿";
		}else if(key.contains("深蓝")){
			keyStr = "深蓝";
		}else if(key.contains("蓝")||key.contains("兰")){
			keyStr = "蓝";
		}else if(key.contains("米")||key.contains("奶茶")){
			keyStr = "米";
		}else if(key.contains("白")){
			keyStr = "白";
		}else if(key.contains("橘")||key.contains("橙")||key.contains("桔")){
			keyStr = "橙";
		}else if(key.contains("檬")||key.contains("柠")){
			keyStr = "柠檬黄";
		}else if(key.contains("黄")){
			keyStr = "黄";
		}else if(key.contains("灰")||key.contains("阴")||key.contains("亚麻色")){
			keyStr = "灰";
		}else if(key.contains("粉")||key.contains("桃")){
			keyStr = "粉";
		}else if(key.contains("紫")||key.contains("薰衣草")){
			keyStr = "紫";
		}else if(key.contains("银")){
			keyStr = "银";
		}else if(key.contains("金")){
			keyStr = "金";
		}else if(key.contains("棕")||key.contains("褐")){
			keyStr = "棕";
		}else if(key.contains("香槟")){
			keyStr = "香槟";
		}else if(key.contains("象牙")){
			keyStr = "象牙";
		}else if(key.contains("咖啡")||key.contains("巧克力")||key.contains("咖")){
			keyStr = "咖啡";
		}else if(key.contains("卡其")||key.contains("茶")){
			keyStr = "卡其";
		}else if(key.contains("杏")){
			keyStr = "杏";
		}else if(key.contains("驼")){
			keyStr = "驼";
		}else if(key.contains("彩")){
			keyStr = "彩";
		}else if(key.contains("多")||key.contains("撞")||key.contains("混")){
			keyStr = "多色";
		}else if(key.contains("裸")){
			keyStr = "裸";
		}else if(key.contains("豹纹")||key.contains("豹")){
			keyStr = "豹纹";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "Picture Color";
		}
		return value;
	}
	
	
	
	
	
	
	public static String getTypesValue(String key) {
		String keyStr = "";
		if(key.contains("长")){
			keyStr = "长";
		}else if(key.contains("修身")||key.contains("紧")||key.contains("瘦")){
			keyStr = "修身";
		}else if(key.contains("松")){
			keyStr = "宽松";
		}else if(key.contains("蝙蝠")){
			keyStr = "蝙蝠";
		}else if(key.contains("披")){
			keyStr = "披肩";
		}else if(key.contains("不对称")){
			keyStr = "不对称";
		}else if(key.contains("大")||key.contains("胖")||key.contains("肥")){
			keyStr = "超大尺寸";
		}else if(key.contains("不对称")){
			keyStr = "不对称";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getStyleValue(String key) {
		String keyStr = "";
		if(key.contains("街")||key.contains("通勤")){
			keyStr = "街头";
		}else if(key.contains("经典")){
			keyStr = "经典";
		}else if(key.contains("时尚")||key.contains("流行")){
			keyStr = "时尚";
		}else if(key.contains("明星")){
			keyStr = "明星";
		}else if(key.contains("国")){
			keyStr = "异国";
		}else if(key.contains("古")||key.contains("老")||key.contains("旧")){
			keyStr = "复古";
		}else if(key.contains("可爱")){
			keyStr = "可爱";
		}else if(key.contains("性感")){
			keyStr = "性感";
		}else if(key.contains("波西米亚")){
			keyStr = "波西米亚";
		}else if(key.contains("度假")){
			keyStr = "度假";
		}else if(key.contains("正式")){
			keyStr = "正式";
		}else if(key.contains("异国")){
			keyStr = "异国";
		}else if(key.contains("运动")){
			keyStr = "运动";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "Casual";
		}
		return value;
	}
	
	public static String getItemValue(String key) {
		String keyStr = "";
		if(key.contains("开衫")||key.contains("开")){
			keyStr = "开衫";
		}else if(key.contains("套头")||key.contains("套")){
			keyStr = "套头";
		}else if(key.contains("大衣")){
			keyStr = "大衣";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getSleeveLengthValue(String key) {
		String keyStr = "";
		if(key.contains("五分")||key.contains("中袖")||key.contains("半袖")){
			keyStr = "半袖";
		}else if(key.contains("长袖")||key.contains("七分袖")||key.contains("直筒")||key.contains("九分袖")){
			keyStr = "长袖";
		}else if(key.contains("短")){
			keyStr = "短袖";
		}else if(key.contains("三分")){
			keyStr = "三分袖";
		}else if(key.contains("拆分")){
			keyStr = "拆分袖";
		}else{
			keyStr = "无袖";
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}	
	
	public static String getNecklineValue(String key) {		
		String keyStr = "";
		if(key.contains("圆")){
			keyStr = "圆领";
		}else if(key.contains("V")){
			keyStr = "V领";
		}else if(key.contains("翻")){
			keyStr = "翻领";
		}else if(key.contains("帽")){
			keyStr = "连帽";
		}else if(key.contains("高")){
			keyStr = "高领";
		}else if(key.contains("船")||key.contains("一")){
			keyStr = "船领";
		}else if(key.contains("心")){
			keyStr = "心领";
		}else if(key.contains("茧")){
			keyStr = "茧领";
		}else if(key.contains("单肩")){
			keyStr = "单肩";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getPatternTypeValue(String key) {
		String keyStr = "";
		if(key.contains("花")){
			keyStr = "花卉";
		}else if(key.contains("纯")){
			keyStr = "纯色";
		}else if(key.contains("圆")||key.contains("点")){
			keyStr = "圆点";
		}else if(key.contains("格")){
			keyStr = "格子";
		}else if(key.contains("条")||key.contains("纹")){
			keyStr = "条纹";
		}else if(key.contains("块")){
			keyStr = "色块";
		}else if(key.contains("拼")){
			keyStr = "拼凑";
		}else if(key.contains("豹")){
			keyStr = "豹";
		}else if(key.contains("动物")||key.contains("龙")||key.contains("猫")||key.contains("狗")||key.contains("鼠")||key.contains("蝴蝶")||key.contains("大象")||key.contains("鸟")){
			keyStr = "动物";
		}else if(key.contains("部")||key.contains("族")){
			keyStr = "部落";
		}else if(key.contains("星")||key.contains("天")||key.contains("空")){
			keyStr = "星系";
		}else if(key.contains("卡通")||key.contains("动画")){
			keyStr = "卡通";
		}else if(key.contains("字")){
			keyStr = "字母";
		}else if(key.contains("骷髅")){
			keyStr = "骷髅";
		}else if(key.contains("刺绣")){
			keyStr = "刺绣";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "Geometric";
		}
		return value;
	}
	
	public static String getMaterialValue(String key) {
		String keyStr = "";
		if(key.contains("纤维")||key.contains("涤纶")||key.contains("聚酯")||key.contains("锦纶")){
			keyStr = "聚酯";
		}else if(key.contains("氨纶")){
			keyStr = "氨纶";
		}else if(key.contains("羊绒混纺")){
			keyStr = "羊绒混纺";
		}else if(key.contains("羊毛混纺")){
			keyStr = "羊毛混纺";
		}else if(key.contains("羊毛")){
			keyStr = "羊毛";
		}else if(key.contains("丝")||key.contains("绸")){
			keyStr = "丝绸";
		}else if(key.contains("棉")){
			keyStr = "棉";
		}else if(key.contains("棉")&&key.contains("混")){
			keyStr = "棉混纺";
		}else if(key.contains("针织")){
			keyStr = "针织";
		}else if(key.contains("腈纶")){
			keyStr = "腈纶";
		}else if(key.contains("莱卡")){
			keyStr = "莱卡";
		}else if(key.contains("雪纺")){
			keyStr = "雪纺";
		}else if(key.contains("牛仔")){
			keyStr = "牛仔";
		}else if(key.contains("天鹅绒")){
			keyStr = "天鹅绒";
		}else if(key.contains("人造丝")){
			keyStr = "人造丝";
		}else if(key.contains("皮革")||key.contains("PU")){
			keyStr = "皮革";
		}else if(key.contains("亚麻")){
			keyStr = "亚麻";
		}else if(key.contains("羊绒")){
			keyStr = "羊绒";
		}else if(key.contains("毛纺")){
			keyStr = "毛纺";
		}else if(key.contains("麂皮绒")){
			keyStr = "麂皮绒";
		}else if(key.contains("灯芯绒")){
			keyStr = "灯芯绒";
		}else if(key.contains("尼龙")){
			keyStr = "尼龙";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "Other";
		}
		return value;
	}
	
	public static String getSeasonValue(String key) {
		String keyStr = "";
		if(key.contains("春")){
			keyStr = "春";
		}else if(key.contains("夏")){
			keyStr = "夏";
		}else if(key.contains("秋")){
			keyStr = "秋";
		}else if(key.contains("冬")){
			keyStr = "冬";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getDressesLengthValue(String key) {
		String keyStr = "";
		if(key.contains("短裙")){
			keyStr = "短裙";
		}else if(key.contains("长裙")){
			keyStr = "长裙";
		}else if(key.contains("超短")||key.contains("迷你")||key.contains("齐B")){
			keyStr = "迷你";
		}else if(key.contains("Maxi")||key.contains("maxi")){
			keyStr = "Maxi";
		}else if(key.contains("膝盖长")||key.contains("膝盖")||key.contains("中裙")){
			keyStr = "膝盖长";
		}else if(key.contains("过膝盖")||key.contains("过膝")){
			keyStr = "过膝盖";
		}else if(key.contains("茶长")||key.contains("茶")){
			keyStr = "茶长";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getWaistTypeValue(String key) {
		String keyStr = "";
		if(key.contains("低腰")||key.contains("低")){
			keyStr = "低腰";
		}else if(key.contains("中腰")||key.contains("中")){
			keyStr = "中腰";
		}else if(key.contains("高腰")||key.contains("高")){
			keyStr = "高腰";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static String getLengthValue(String key) {
		String keyStr = "";
		if(key.contains("短裤")||key.contains("热裤")){
			keyStr = "短裤";
		}if(key.contains("长裤")){
			keyStr = "长裤";
		}else{
			keyStr = key;
		}
		String value = getValue(keyStr);
		if(value==null){
			value = "";
		}
		return value;
	}	
	
	public static String trimProductName(String name) {
		String result = "";
		if(name!=null&&!name.equals("")){
			result = name.replaceAll("[^a-zA-Z\\u4e00-\\u9fa5]", "").replaceAll("春装", "").replaceAll("夏装", "").replaceAll("秋装", "").replaceAll("冬装", "").replaceAll("春款", "").replaceAll("夏款", "").replaceAll("秋款", "").replaceAll("冬款", "")
			.replaceAll("秋冬", "").replaceAll("秋冬", "").replaceAll("春秋", "").replaceAll("春夏", "").replaceAll("春", "").replaceAll("夏", "").replaceAll("秋", "").replaceAll("冬", "").replaceAll("季", "")
			.replaceAll("热卖", "").replaceAll("专柜正品", "").replaceAll("正品", "").replaceAll("新品", "").replaceAll("单品", "").replaceAll("新款", "").replaceAll("特价", "").replaceAll("包邮", "").replaceAll("清仓", "")
			.replaceAll("女装", "").replaceAll("女裤", "").replaceAll("美女", "").replaceAll("新", "").replaceAll("女", "").replaceAll("欧美", "").replaceAll("潮流", "").replaceAll("潮", "").replaceAll("体验", "").replaceAll("潮牌", "").replaceAll("纯色运动", "").replaceAll("色", "").replaceAll("码全", "");
		}
		return result;
	}
	
	
	public static String getVanclColor(String key) {
		String keyStr = "";
		if(key.contains("white")||key.contains("cotton candy")){
			keyStr = "White";
		}else if(key.contains("black")||key.contains("sepia")){
			keyStr = "Black";
		}else if(key.contains("green")||key.contains("lawn")||key.contains("turquoise")||key.contains("cyan")||key.contains("fern")||key.contains("olive")){
			keyStr = "Green";
		}else if(key.contains("red")||key.contains("watermelon")||key.contains("magenta")||key.contains("saffron")){
			keyStr = "Red";
		}else if(key.contains("gray")||key.contains("charcoal")){
			keyStr = "Grey";
		}else if(key.contains("blue")||key.contains("denim")){
			keyStr = "Blue";
		}else if(key.contains("yellow")||key.contains("ginger")){
			keyStr = "Yellow";
		}else if(key.contains("pink")||key.contains("coral")){
			keyStr = "Pink";
		}else if(key.contains("purple")||key.contains("rose")||key.contains("lavender")||key.contains("violet")||key.contains("maroon")||key.contains("orchid")){
			keyStr = "Purple";
		}else if(key.contains("silver")){
			keyStr = "Silver";
		}else if(key.contains("gold")){
			keyStr = "Gold";
		}else if(key.contains("brown")||key.contains("raw umber")||key.contains("bronze")||key.contains("light tan")){
			keyStr = "Brown";
		}else if(key.contains("orange")||key.contains("tangerine")||key.equals("jacinth")){
			keyStr = "Orange";
		}else if(key.contains("beige")||key.contains("carnation")){
			keyStr = "Beige";
		}else if(key.contains("ivory")){
			keyStr = "Ivory";
		}else if(key.contains("coffee")||key.contains("cocoa")){
			keyStr = "Coffee";
		}else if(key.equals("navy blue")||key.contains("navy")||key.contains("sapphire blue")||key.equals("deep blue")){
			keyStr = "Navy";
		}else if(key.contains("khaki")){
			keyStr = "Khaki";
		}else if(key.contains("apricot")){
			keyStr = "Apricot";
		}else if(key.contains("camel")){
			keyStr = "Camel";
		}else if(key.contains("leopard")){
			keyStr = "Leopard";
		}else if(key.contains("multi")||key.contains("colorful")){
			keyStr = "Multi";
		}else if(key.contains("nude")){
			keyStr = "Nude";
		}else{
			keyStr = "Picture Color";
		}
		return keyStr;
	}
	
	public static String getWeight(String category) {	
		String value = null;
		try{
		 value = resourceBundle.getString(category+"_Weight");//
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("-------error---key--------"+category);
		}	
		return value;

	}
	
	public static double getPriceMax(String category) {	
		double value = 300;
		try{
		 String valueStr = resourceBundle.getString(category+"_MaxPrice");//
		 if(valueStr!=null&&!valueStr.equals("")){
			 value = Double.valueOf(valueStr); 
		 }
		 
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("-------error---key--------"+category);
		}	
		return value;
	}
	
	public static double getExpressFee(String category) {	
		double free = 100;
		try{
		 String valueStr = resourceBundle.getString(category+"_ExpressFree");//
		 if(valueStr!=null&&!valueStr.equals("")){
			 free = Double.valueOf(valueStr); 
		 }
		 
		}catch (Exception e) {
			//e.printStackTrace();
			System.out.println("-------error---key--------"+category);
		}	
		return free;
	}
	
	
	
	public static String getCategory(String productName) {	
		String category = null;
		if(productName.contains("毛衣")||productName.contains("针织衫")){
			category = "Tops/Sweaters";
		}else if(productName.contains("运动衫")||productName.contains("卫衣")){
			category = "Tops/Sweatshirts";
		}else if(productName.contains("外套")||productName.contains("大衣")||productName.contains("风衣")||productName.contains("夹克")||productName.contains("皮衣")||productName.contains("马甲")){
			category = "Tops/Outerwear";
		}else if(productName.contains("衬衫")||productName.contains("衬衣")){
			category = "Tops/Blouses";
		}else if(productName.contains("西装")||productName.contains("西服")){
			category = "Tops/Suits";
		}else if(productName.contains("T恤")){
			category = "Tops/T-Shirt";
		}else if(productName.contains("连衣裙")){
			category = "Dresses/Fashion Dresses";
		}else if(productName.contains("裤")){
			category = "Bottoms/Pants";
		}else if(productName.contains("长裙")||productName.contains("短裙")){
			category = "Bottoms/Skirts";
		}else if(productName.contains("靴子")){
			category = "Shoes/Boots";
		}else if(productName.contains("帆布鞋")){
			category = "Shoes/Canvas Shoes";
		}else if(productName.contains("平底鞋")){
			category = "Shoes/Flats";
		}else if(productName.contains("高跟鞋")){
			category = "Shoes/Heels";
		}else if(productName.contains("休闲鞋")){
			category = "Shoes/Leisure Shoes";
		}else if(productName.contains("运动鞋")){
			category = "Shoes/Sneakers";
		}else if(productName.contains("鞋")&&(productName.contains("培训")||productName.contains("训练"))){
			category = "Shoes/Trainers";
		}else if(productName.contains("项链")||productName.contains("毛衣链")){
			category = "Jewelry/Necklaces";
		}else if(productName.contains("耳环")){
			category = "Jewelry/Earrings";
		}else if(productName.contains("手镯")){
			category = "Jewelry/Bracelets";
		}else if(productName.contains("戒指")){
			category = "Jewelry/Rings";
		}else if(productName.contains("袖扣")){
			category = "Accessories/Cufflinks";
		}else if(productName.contains("项圈")){
			category = "Accessories/Collars";
		}else if(productName.contains("帽子")){
			category = "Accessories/Hats";
		}	
		return category;

	}
	
	public static String getAttributeSet(String category) {	
		String attributeSet = null;
		if(category.equals("Tops/Sweaters")){
			attributeSet = "Sweaters_set";
			
		}else if(category.equals("Tops/Sweatshirts")){
			attributeSet = "Sweatshirts_set";
			
		}else if(category.equals("Tops/Outerwear")){
			attributeSet = "Outerwear_set";
			
		}else if(category.equals("Tops/Blouses")){
			attributeSet = "Blouses_set";
			
		}else if(category.equals("Tops/Suits")){
			attributeSet = "Suits_set";
			
		}else if(category.equals("Tops/T-Shirt")){
			attributeSet = "TShirt_set";
			
		}else if(category.equals("Dresses/Fashion Dresses")){
			attributeSet = "fashion_dresses_set";
			
		}else if(category.equals("Bottoms/Pants")){
			attributeSet = "Pants_set";
			
		}else if(category.equals("Bottoms/Skirts")){
			attributeSet = "Skirts_set";
			
		}else if(category.startsWith("Shoes")){
			attributeSet = "Shoes_set";
			
		}else if(category.equals("Jewelry/Necklaces")){
			attributeSet = "Necklaces_set";
			
		}else if(category.equals("Jewelry/Earrings")){
			attributeSet = "Earrings_set";
			
		}else if(category.equals("Jewelry/Bracelets")){
			attributeSet = "Bracelets_set";
			
		}else if(category.equals("Jewelry/Rings")){
			attributeSet = "Rings_set";
			
		}else if(category.equals("Accessories/Cufflinks")){
			attributeSet = "Cufflinks_set";
			
		}else if(category.equals("Accessories/Collars")){
			attributeSet = "Collars_set";
			
		}else if(category.equals("Accessories/Hats")){
			attributeSet = "Hats_set";
			
		}	
		return attributeSet;

	}
	
	public static String getCategoryOption(String category) {	
		String option = null;
		if(category.contains("Tops")||category.contains("Dresses")){
			option = "Tops";
			
		}else if(category.contains("Bottoms")){
			option = "Bottoms";
			
		}else if(category.contains("Shoes")){
			option = "Shoes";
			
		}else{
			option = "other";
			
		}
		return option;

	}
	
	public static double getDollarRate() {//	
		double value = 6.3;
		 String valueStr = resourceBundle.getString("Dollar_rate");//
		 if(valueStr!=null&&!valueStr.equals("")){
			 value = Double.valueOf(valueStr); 
		 }
		return value;
	}
	
	

}
