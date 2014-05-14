package com.shebuyit.crawler.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shebuyit.crawler.file.CSVFile;
import com.shebuyit.crawler.jsoup.Crawler;
import com.shebuyit.crawler.util.DataDictionary;
import com.shebuyit.crawler.util.SystemConfig;
import com.shebuyit.po.Product;
import com.shebuyit.po.ProductMedia;
import com.shebuyit.po.ProductOption;
import com.shebuyit.po.ProductOptionRow;
import com.shebuyit.po.Shop;

public class TianmaoShopBottomsCrawler extends Crawler {
		
	private String shopSku;
	
	private String shopBrand;
	
	private List<String> shopName;
	
	private String category;
	private String attributeSet;
		
	private static String PAGEURL = "";
	
	private List<Product> productList = new ArrayList<Product>();
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		doStart(shop);
	}
	
	private static int productNumber = 1;
	
	public void doStart(Shop shop) {
		String indexURL = shop.getShopUrl();
		
		JSONArray array = new JSONArray();
		int pageCount = getPageCount(indexURL);
		PAGEURL = indexURL.replaceAll("pageNum=1", "pageNum=%p");
		int csvfileCount = 1;
		if(pageCount>10){
			for (int i = 1; i < pageCount+1; i++) {
				String pageUrl = PAGEURL.replaceAll("%p", i + "");				
				start(pageUrl, array);
				if(i%10==0){
					CSVFile.createCSVFile(this.getCategory(),productList, SystemConfig.File_Path(), "Taobao-"+category.toLowerCase().replaceAll("/", "-")+"-"+this.shopSku+"-Product-"+csvfileCount);
					csvfileCount++;
					productList = new ArrayList<Product>();
				}
			}
			CSVFile.createCSVFile(this.getCategory(),productList, SystemConfig.File_Path(), "Taobao-"+category.toLowerCase().replaceAll("/", "-")+"-"+this.shopSku+"-Product-"+csvfileCount);			
		}else{
			for (int i = 1; i < pageCount+1; i++) {
				String pageUrl = PAGEURL.replaceAll("%p", i + "");
				start(pageUrl, array);				
					
			}
			CSVFile.createCSVFile(this.getCategory(),productList, SystemConfig.File_Path(), "Taobao-"+category.toLowerCase().replaceAll("/", "-")+"-"+this.shopSku+"-Product");
		
		}
		
		//System.out.println(array);
	}
	
	public int getPageCount(String url) {
		int pageCount = 1;
		try {
			//org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			
			String content= super.doGet(url);
			Document doc = Jsoup.parse(content);
			
			Elements listPages = doc.getElementsByClass("page-info");
			if (listPages != null) {
				Element pageALink = listPages.first();
				if (pageALink != null) {
					String pageNumber = pageALink.text().substring(pageALink.text().indexOf("/")+1);
					pageCount = Integer.parseInt(pageNumber.trim());
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("---total------pageCount--------" + pageCount);
		return pageCount;
	}
	
	
	public void start(String url, JSONArray array) {
		try {
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			Elements listProducts = doc.getElementsByClass("item");
			JSONArray tempJsonArray = new JSONArray();
			for (Element ele : listProducts) {
								
				String productUrl = ele.select("a").first().attr("href");				
				String productId = productUrl.substring(productUrl.indexOf("=")+1, productUrl.indexOf("&")-1);
				
				
				if(productId!=null&&!productId.equals("")){					
					String prouductOrlPrice = ele.select("strong").first().text();	
					if(prouductOrlPrice!=null&&!prouductOrlPrice.equals("")){
						String productSpecialPriceE = ele.select("div").get(3)==null?null:ele.select("div").get(3).toString();	
						String productSpecialPriceStr = "";
						if(productSpecialPriceE==null){
							productSpecialPriceStr = prouductOrlPrice;
						}else{
							productSpecialPriceStr = productSpecialPriceE.substring(productSpecialPriceE.indexOf("折扣价是")+5, productSpecialPriceE.indexOf("原价是")-1);
						}
						double prouductPrice = Double.valueOf(productSpecialPriceStr);
						double priceMax = DataDictionary.getPriceMax(this.category.replaceAll(" ", "_"));
						if(prouductPrice<priceMax){
							Product product = new Product();
							
														
							double expressFee = DataDictionary.getExpressFee(this.shop.getCategory().replaceAll(" ", "_"));			
							if(productSpecialPriceE==null){
								double price = (prouductPrice+expressFee)*1.5/DataDictionary.getDollarRate();							
								product.setPrice(String.format("%.2f",price));	
													
							}else{
								double price = (Double.parseDouble(prouductOrlPrice)+expressFee)*1.5/DataDictionary.getDollarRate();
								double special_price = (prouductPrice+expressFee)*1.5/DataDictionary.getDollarRate();								
								product.setPrice(String.format("%.2f",price));	
								product.setSpecial_price(String.format("%.2f",special_price));	
							}
													

							product = parseProductInfoUrl(productUrl,productId,product);	
							if(product!=null){
								productList.add(product);
								System.out.println(productNumber+"---------productNumber--------------------------------------------------------------");
								System.out.println("---------prouductPrice----------------"+prouductPrice);
								productNumber++;	
							}						
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("productUrl", productUrl);
							tempJsonArray.put(jsonObject);						
						}
					}
				}
				
				
								
				
			}
			array.put(tempJsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

     //	解析product 信息
	public Product parseProductInfoUrl(String productUrl,String productId,Product product) {
		System.out.println("productUrl--------------" + productUrl);
		try {
			String productSku = this.getShopSku() + productId;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setUpdated_at(format.format(new Date()));
			product.setCategory(this.category);
			product.setAttribute_set(this.attributeSet);
			product.setSb_shop(productUrl);						
			product.setMedia_gallery("Media_gallery");
			product.setWeight("1");
			product.setSku(productSku);
			product.setSb_brand(this.getShopBrand());
			
			Thread.sleep(30000);
			//org.jsoup.nodes.Document doc = Jsoup.connect(productUrl).get();			
			String content = super.doGet(productUrl);
			Document doc = Jsoup.parse(content);
			if(0==0){							
				Elements productNames = doc.getElementsByClass("tb-detail-hd");
				String productName = productNames.first().select("a").first().text();
				for(String shopNameStr:this.getShopName()){
					productName = productName.replaceAll(shopNameStr, "");
				}			
				productName = youDaoTranslateEnToCinese(DataDictionary.trimProductName(productName));
				product.setName(productName);
				product.setMeta_title(productName);
				product.setMeta_keyword(productName);
				product.setMeta_description(productName+" on sale at reasonable prices. Buy cheap Dress at SheBuyIt.com");
				product.setShort_description(productName);
				String urlKey = productName.toLowerCase().replaceAll(" ", "-");
				product.setUrl_key(urlKey);
				product.setUrl_path(urlKey + ".html");
								
				
				
				
//				Element productColorElement = doc.getElementById("selectedColorName");
//				String color = productColorElement.text();
//				product.setSb_color("Red");
				

				List<ProductOption> productOptionList = new ArrayList<ProductOption>();
				ProductOption sizeOption = new ProductOption();
				Elements optionSizeClass = doc.getElementsByClass("J_TSaleProp");
				Elements optionSizes = optionSizeClass.first().select("span");
				if (optionSizes != null && optionSizes.size() > 0) {
					product.setHas_options("1");
					product.setRequired_options("1");
					List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

					sizeOption.setCustom_option_title("Size");
					sizeOption.setCustom_option_type("drop_down");
					sizeOption.setCustom_option_is_required("1");
					sizeOption.setCustom_option_sort_order("1");

					Iterator optionSizeIterator = optionSizes.iterator();
					int i = 1;
					while (optionSizeIterator.hasNext()) {
						Element option = (Element) optionSizeIterator.next();
						String optionValue = option.text();						
						
						//optionValue = DataDictionary.getPantsSizeValue(optionValue);
						optionValue = DataDictionary.getSizeValue(optionValue);
						
						System.out.println("size--------------" + optionValue);
						if(optionValue!=null&&!optionValue.equals("")){
							ProductOptionRow productOptionRow = new ProductOptionRow();
							productOptionRow.setCustom_option_row_title(optionValue);
							productOptionRow.setCustom_option_row_sort(i + "");
							productOptionRowList.add(productOptionRow);
							i++;
						}else{
							int e= 1/0;
						}					
					}
					sizeOption.setOption_rows(productOptionRowList);
				}
				productOptionList.add(sizeOption);
				
				StringBuilder productColorSB = new StringBuilder();
				Elements productColorOptions = doc.getElementsByClass("tb-img");
				if(productColorOptions != null){
					ProductOption colorOption = new ProductOption();
					Elements optionColors	 = productColorOptions.first().select("span");
					if (optionColors != null && optionColors.size() > 0) {
						List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();
						colorOption.setCustom_option_title("Color");
						colorOption.setCustom_option_type("drop_down");
						colorOption.setCustom_option_is_required("1");
						colorOption.setCustom_option_sort_order("2");

						Iterator optionColorIterator = optionColors.iterator();
						int i = 1;
						while (optionColorIterator.hasNext()) {
							
							Element option = (Element) optionColorIterator.next();
							String optionValue = option.text();						
												
							optionValue = DataDictionary.getColorValue(optionValue);
							if(optionValue!=null&&!optionValue.equals("")){
								ProductOptionRow productOptionRow = new ProductOptionRow();								
								productOptionRow.setCustom_option_row_title(optionValue);
								productOptionRow.setCustom_option_row_sort(i + "");
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								productColorSB.append(optionValue).append(";");
								i++;
							}
						}
						colorOption.setOption_rows(productOptionRowList);
					}
					productColorSB.deleteCharAt(productColorSB.lastIndexOf(";"));
					//Height(Inches)Better For Fitting Your Size			
					ProductOption heightOption = new ProductOption();
					heightOption.setCustom_option_title("Height(Inches) Better For Fitting Your Size");
					heightOption.setCustom_option_type("field");
					heightOption.setCustom_option_is_required("0");
					heightOption.setCustom_option_sort_order("3");
					
					//Weight(Ibs)Better For Fitting Your Size			
					ProductOption weightOption = new ProductOption();
					weightOption.setCustom_option_title("Weight(Ibs) Better For Fitting Your Size");
					weightOption.setCustom_option_type("field");
					weightOption.setCustom_option_is_required("0");
					weightOption.setCustom_option_sort_order("4");
					
					productOptionList.add(heightOption);
					productOptionList.add(weightOption);

					productOptionList.add(colorOption);
				}else{
					//Height(Inches)Better For Fitting Your Size			
					ProductOption heightOption = new ProductOption();
					heightOption.setCustom_option_title("Height(Inches) Better For Fitting Your Size");
					heightOption.setCustom_option_type("field");
					heightOption.setCustom_option_is_required("0");
					heightOption.setCustom_option_sort_order("2");
					
					//Weight(Ibs)Better For Fitting Your Size			
					ProductOption weightOption = new ProductOption();
					weightOption.setCustom_option_title("Weight(Ibs) Better For Fitting Your Size");
					weightOption.setCustom_option_type("field");
					weightOption.setCustom_option_is_required("0");
					weightOption.setCustom_option_sort_order("3");
					
					productOptionList.add(heightOption);
					productOptionList.add(weightOption);
				}
				
				product.setProduct_options(productOptionList);
				

				StringBuilder elementSb = new StringBuilder();
				elementSb.append("<ul>");
				Element productDescriptionE = doc.getElementById("J_AttrList"); 
				Elements descriptionEs = productDescriptionE.select("li");
				for (Element element : descriptionEs) {
					String elementStr = element.text();

					if(elementStr.startsWith("板型")){
						elementSb.append("<li><stong>Types :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String typesValue = DataDictionary.getTypesValue(elementStrValue);
						elementSb.append(typesValue);
						elementSb.append("</i></li>");		
						//product.setSb_types(typesValue);
					}else if(elementStr.startsWith("风格")){
						elementSb.append("<li><stong>Style :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String styleValue = DataDictionary.getStyleValue(elementStrValue);
						elementSb.append(styleValue);
						elementSb.append("</i></li>");	
						product.setSb_style(styleValue);
					}else if(elementStr.startsWith("款式")){
						elementSb.append("<li><stong>Item :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String itemValue = DataDictionary.getItemValue(elementStrValue);
						elementSb.append(itemValue);
						elementSb.append("</i></li>");	
						product.setSb_item(itemValue);
					}else if(elementStr.startsWith("袖长")){
						elementSb.append("<li><stong>Sleeve Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);	
						String sleeveLengthValue = DataDictionary.getSleeveLengthValue(elementStrValue);
						elementSb.append(sleeveLengthValue);
						elementSb.append("</i></li>");		
						product.setSb_sleeve_length(sleeveLengthValue);
					}else if(elementStr.startsWith("领子")||elementStr.startsWith("女装领型")){
						elementSb.append("<li><stong>Neckline :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String necklineValue = DataDictionary.getNecklineValue(elementStrValue);
						elementSb.append(necklineValue);
						elementSb.append("</i></li>");		
						product.setSb_neckline(necklineValue);
					}else if(elementStr.startsWith("裙长")){
						elementSb.append("<li><stong>Dresses Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String dressesLengthValue = DataDictionary.getDressesLengthValue(elementStrValue);
						elementSb.append(dressesLengthValue);
						elementSb.append("</i></li>");
						product.setSb_dresses_length(dressesLengthValue);
					}else if(elementStr.startsWith("腰型")||elementStr.contains("腰")){
						elementSb.append("<li><stong>Waist Type :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String waistTypeValue = DataDictionary.getWaistTypeValue(elementStrValue);
						elementSb.append(waistTypeValue);
						elementSb.append("</i></li>");
						product.setSb_waist_type(waistTypeValue);
					}else if(elementStr.startsWith("图案")){
						elementSb.append("<li><stong>Pattern Type :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);	
						String patternTypeValue = DataDictionary.getPatternTypeValue(elementStrValue);
						elementSb.append(patternTypeValue);
						elementSb.append("</i></li>");	
						product.setSb_pattern_type(patternTypeValue);
					}else if(elementStr.startsWith("面料:")||elementStr.startsWith("面料材质:")||elementStr.startsWith("材质:")){
						elementSb.append("<li><stong>Material :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String materialValue = DataDictionary.getMaterialValue(elementStrValue);
						elementSb.append(materialValue);
						elementSb.append("</i></li>");	
						product.setSb_material(materialValue);
					}else if(elementStr.startsWith("颜色分类")){
						elementSb.append("<li><stong>Color :</stong>").append("<i>");
						elementSb.append(productColorSB.toString());
						elementSb.append("</i></li>");
					}else if(elementStr.contains("年份")){
						elementSb.append("<li><stong>Season :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String seasonValue = DataDictionary.getSeasonValue(elementStrValue);
						elementSb.append(seasonValue);
						elementSb.append("</i></li>");	
						product.setSb_season(seasonValue);
					}
				}
				///针对bottoms
				product.setSb_types("Leggings");
				//product.setSb_style("Casual");

				elementSb.append("</ul>");
				String productDescription = elementSb.toString();
				//productDescription = super.translateEnToCinese(productDescription);
				productDescription = productDescription.replaceAll("\r|\n", "").replaceAll(",", " ");
				
				List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();
				
				StringBuilder descriptionSb = new StringBuilder();
				descriptionSb.append("<div>").append(productDescription);
				descriptionSb.append("<br/><br/><img  src='{{media url='upload/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-size.jpg'}}' /><br/><br/>");
				descriptionSb.append("</div>");
				descriptionSb.append("<div style='clear:both;'></div>");
				descriptionSb.append("<div>");
				int imgNum = 0;
				Elements productImages = doc.getElementsByClass("tb-s60");		
				if (productImages != null) {
					
					for (int i = 0; i < productImages.size(); i++) {
						ProductMedia productMedia = new ProductMedia();
						Element productImage = productImages.get(i);
						String imageUrl = productImage.select("img").first().absUrl("src").replaceAll("60x60", "460x460");

						if (i == 0) {
							product.setImage("/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
							product.setSmall_image("/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
							product.setThumbnail("/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
						}
						productMedia.setMedia_image("/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-" + (i + 1)
								+ ".jpg");
						productMedia.setMedia_position((i + 1) + "");
						ProductMediaList.add(productMedia);
						
						//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
						//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
						super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/taobao/"+category+"/"+this.shopSku+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
						descriptionSb.append("<img src='{{media url='upload/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-" + (i + 1)+ ".jpg'}}' /><br/><br/>");
						imgNum = i+1;
					}				
				}
				
			
				descriptionSb.append("</div>");
				Elements productColors = doc.getElementsByClass("tb-img");
				if(productColors != null){
					Elements productColorImages	 = productColors.first().select("a");
					if (productColorImages != null) {
						String productColorStr = productColorSB.toString();
						String[] productColorArray  = productColorStr.split(";");
						for (int i = 0; i < productColorImages.size(); i++) {									
							ProductMedia productMedia = new ProductMedia();
							Element productImage = productColorImages.get(i);
							String imageStr= productImage.attr("style");
							System.out.println("imageStr--------------" + imageStr);
							if(imageStr!=null&&!"".equals(imageStr)){
								descriptionSb.append("<div>");		
								String imageUrl = imageStr.substring(imageStr.indexOf("(")+1, imageStr.indexOf(")")).replaceAll("30x30", "460x460");
								
								productMedia.setMedia_image("/taobao/"+this.category+"/"+this.shopSku+"/" + product.getSku() + "-" + (imgNum + 1)+ ".jpg");
								productMedia.setMedia_position((imgNum + 1) + "");
								ProductMediaList.add(productMedia);
								
								//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
								//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
								super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/taobao/"+category+"/"+this.shopSku+"/"+product.getSku() + "-"+ (imgNum + 1)+ ".jpg");
								descriptionSb.append("<img src='{{media url='upload/taobao/"+this.category+"/"+this.shopSku+"/"+ product.getSku() + "-" + (imgNum + 1)+ ".jpg'}}' />");
								descriptionSb.append("<br></br><table border='0' width='50%'><tr><td style='text-align:center;' ><stong>"+productColorArray[i]+"</stong></td></tr></table><br></br>");
								descriptionSb.append("</div>");
								imgNum++;
							}							
						}
						
					}
				}
				
				product.setDescription(descriptionSb.toString());
				product.setProduct_medias(ProductMediaList);
				
				}
			} catch (Exception e) {
				e.printStackTrace();
				product = null;
			}				
		return product;			
	}
	
	
	
	
	public String getAttributeSet() {
		return attributeSet;
	}

	public void setAttributeSet(String attributeSet) {
		this.attributeSet = attributeSet;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public List<String> getShopName() {
		return shopName;
	}

	public void setShopName(List<String> shopName) {
		this.shopName = shopName;
	}

	public String getShopSku() {
		return shopSku;
	}

	public void setShopSku(String shopSku) {
		this.shopSku = shopSku;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getShopBrand() {
		return shopBrand;
	}

	public void setShopBrand(String shopBrand) {
		this.shopBrand = shopBrand;
	}
	

}