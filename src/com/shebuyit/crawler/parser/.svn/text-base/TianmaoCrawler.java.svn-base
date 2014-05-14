package com.shebuyit.crawler.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shebuyit.crawler.jsoup.Crawler;
import com.shebuyit.crawler.util.DataDictionary;
import com.shebuyit.crawler.util.SystemConfig;
import com.shebuyit.po.Config;
import com.shebuyit.po.Dict;
import com.shebuyit.po.Product;
import com.shebuyit.po.ProductMedia;
import com.shebuyit.po.ProductOption;
import com.shebuyit.po.ProductOptionRow;
import com.shebuyit.po.Shop;
import com.shebuyit.service.IConfigService;
import com.shebuyit.service.IDictService;
import com.shebuyit.service.IProductService;
import com.shebuyit.service.IShopService;

public class TianmaoCrawler extends Crawler {
		
	private IProductService productService;
	
	private IShopService shopService;
	
	private IConfigService configService;
	
	private IDictService dictService;
			
	private static TianmaoCrawler tianmao;
	
		
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public IShopService getShopService() {
		return shopService;
	}

	public void setShopService(IShopService shopService) {
		this.shopService = shopService;
	}
	
	
	public IConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}

	public IDictService getDictService() {
		return dictService;
	}

	public void setDictService(IDictService dictService) {
		this.dictService = dictService;
	}

	public void init()

    {

		tianmao = this;

		tianmao.productService = this.productService;
		
		tianmao.configService = this.configService;
		
		tianmao.shopService = this.shopService;

    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		doStart(shop);
	}
	
	private static int productNumber = 1;
	
	public void doStart(Shop shop) {
		System.out.println("------Parser---Start---------");
		String indexURL = shop.getShopUrl();
		int pageCount = getPageCount(indexURL);
		String tempPageURL = indexURL.replaceAll("pageNum=1", "pageNum=%p");	
		for (int i = 1; i < pageCount+1; i++) {
			String pageUrl = tempPageURL.replaceAll("%p", i + "");				
			start(pageUrl);
		}
		System.out.println("------Parser---End---------");
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
	
	
	public void start(String url) {
		try {
//			String content = super.doGet(url);
//			Document doc = Jsoup.parse(content);
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements listProducts = doc.getElementsByClass("item");
			for (Element ele : listProducts) {
								
				String productUrl = ele.select("a").first().attr("href");				
				String productId = productUrl.substring(productUrl.indexOf("=")+1, productUrl.indexOf("&")-1);
				
				
				if(productId!=null&&!productId.equals("")){					
					String prouductOrlPriceStr = ele.getElementsByClass("c-price").first().text();	
					if(prouductOrlPriceStr!=null&&!prouductOrlPriceStr.equals("")){
///						String productSpecialPriceE = ele.select("div").get(3)==null?null:ele.select("div").get(3).toString();	
						String productSpecialPriceStr = "";
//						if(productSpecialPriceE!=null&&productSpecialPriceE.contains("折扣价是")){							
//							productSpecialPriceStr = productSpecialPriceE.substring(productSpecialPriceE.indexOf("折扣价是")+5, productSpecialPriceE.indexOf("原价是")-1);
//						}
						double prouductOrlPrice = Double.valueOf(prouductOrlPriceStr);
						double productSpecialPrice;
						if(!productSpecialPriceStr.equals("")){
							productSpecialPrice = Double.valueOf(productSpecialPriceStr);
						}else{
							productSpecialPrice = prouductOrlPrice;
						}
						
						
						double priceMax = DataDictionary.getPriceMax(this.shop.getCategory().replaceAll(" ", "_"));
						if(productSpecialPrice<priceMax){
							Product product = new Product();
							
							//判断product是否已经存在
							Product existProduct = new Product();
							existProduct.setProductSku(productId);
							existProduct.setShopCanal("tianmao");
							List<Product> existProducts = tianmao.productService.queryProduct(existProduct);
							if(existProducts!=null&&existProducts.size()>0){
								System.out.println("product---------"+productId+"----------------------exist-----------------------------------");						
								continue;
							}
							
							//获得sku序号,设置sku
							Config config = new Config();
							config.setConfigName("skuNumber");
							List<Config> configs = tianmao.configService.queryConfig(config);
							if(configs!=null&&configs.size()>0){
								Config con = configs.get(0);
								Long skuNumber = Long.valueOf(con.getConfigValue())+1;
								System.out.println("product---skuNumber------"+skuNumber);						
								
								product.setSku("SKU"+skuNumber.toString());
								
								//保存新的sku序号
								con.setConfigValue(skuNumber.toString());
								tianmao.configService.update(con);				
							}else{
								continue;
							}	
							
							
							double expressFee = DataDictionary.getExpressFee(this.shop.getCategory().replaceAll(" ", "_"));	
							
												
							if(productSpecialPrice<=50){
								double special_price = (productSpecialPrice*2.2+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(productSpecialPrice>50&&productSpecialPrice<=100){
								double special_price = (productSpecialPrice*2+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(productSpecialPrice>100&&productSpecialPrice<=130){
								double special_price = (productSpecialPrice*1.9+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(productSpecialPrice>130&&productSpecialPrice<=160){
								double special_price = (productSpecialPrice*1.8+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(productSpecialPrice>160&&productSpecialPrice<=200){
								double special_price = (productSpecialPrice*1.7+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(productSpecialPrice>200){
								double special_price = (productSpecialPrice*1.6+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}
							product.setOrl_price(String.format("%.2f",productSpecialPrice));							

							product = parseProductInfoUrl(productUrl,productId,product);	
							if(product!=null){
								tianmao.productService.save(product);
								System.out.println(productNumber+"---------productNumber--------------------------------------------------------------");
								System.out.println("---------prouductPrice----------------"+productSpecialPrice);
								productNumber++;	
							}											
						}
					}
				}
				
				
								
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

     //	解析product 信息
	public Product parseProductInfoUrl(String productUrl,String productId,Product product) {
		System.out.println("productUrl--------------" + productUrl);
		try {
			Thread.sleep(30000);
			
			String productSku = productId;
			//随机生成50到200随机数作为库存
			Long stock =  Math.round(Math.random()*(200-50)+50);
			product.setQty(stock.toString());
			product.setStock(1);
			product.setIsExport(0);
			product.setSaleCount(0);
			product.setShopCanal("tianmao");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setUpdated_at(format.format(new Date()));
			product.setCategory(this.shop.getCategory());
			product.setAttribute_set(this.shop.getAttribute_set());
			product.setSb_shop(productUrl);		
			product.setShopSku(this.shop.getShopSku());
			product.setMedia_gallery("Media_gallery");
			String weight = DataDictionary.getWeight(this.shop.getCategory().replaceAll(" ", "_"));
			product.setWeight(weight);
			product.setProductSku(productSku);
			product.setSb_brand(this.shop.getShopBrand());
			
			
			//org.jsoup.nodes.Document doc = Jsoup.connect(productUrl).get();			
			String content = super.doGet(productUrl);
			Document doc = Jsoup.parse(content);
			if(0==0){							
				Elements productNames = doc.getElementsByClass("tb-detail-hd");
				String productName = productNames.first().select("h3").first().text();
				
				System.out.println("---------chineseName----------------"+productName);
				product.setChineseName(productName);			
				String translateName = translateEnToCineseBing(DataDictionary.trimProductName(productName));
				System.out.println("-------translate--englishName----------------"+translateName);
				
				StringBuilder englishNameB = new StringBuilder();
				//数据字典
				String[] nameArray = translateName.split(" ");
				if(nameArray!=null){
					for(int i = 0;i<nameArray.length;i++){
						String nameDict = nameArray[i];
						Dict dict = new Dict();
						dict.setName(nameDict);
						List<Dict> dicts = tianmao.dictService.queryDict(dict);
						if(dicts.size()>0){			
							//保存数据字典
							if(englishNameB.indexOf(nameDict)<0){
								englishNameB.append(nameDict).append(" ");
							}
						}else{
							continue;
						}
					}
				}				
				
				System.out.println("---------englishName----------------"+englishNameB.toString());
				product.setEnglishName(englishNameB.toString());

			
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
						
						optionValue = DataDictionary.getSizeValue(optionValue);
						System.out.println("size--------------" + optionValue);
						if(optionValue!=null&&!optionValue.equals("")){
							ProductOptionRow productOptionRow = new ProductOptionRow();
							if(optionValue.toLowerCase().contains("one")||optionValue.toLowerCase().contains("free")){
								product.setIsFreeSize(1);
							}else{
								product.setIsFreeSize(0);								
							}
							productOptionRow.setCustom_option_row_title(optionValue);
							productOptionRow.setCustom_option_row_sort(i + "");
							productOptionRow.setProductOption(sizeOption);
							productOptionRowList.add(productOptionRow);
							i++;
						}						
					}
					sizeOption.setOption_rows(productOptionRowList);
				}
				sizeOption.setProduct(product);
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
								productOptionRow.setProductOption(colorOption);
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								productColorSB.append(optionValue).append(";");
								i++;
							}
						}
						colorOption.setProduct(product);
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
					
					String categoryOption = DataDictionary.getCategoryOption(this.shop.getCategory());
					if(categoryOption.equals("Tops")){
						//Bust(Inches) Better For Fitting Your Size			
						ProductOption bustOption = new ProductOption();
						bustOption.setCustom_option_title("Bust(Inches) Better For Fitting Your Size");
						bustOption.setCustom_option_type("field");
						bustOption.setCustom_option_is_required("0");
						bustOption.setCustom_option_sort_order("5");
						bustOption.setProduct(product);
						productOptionList.add(bustOption);
					}else if(categoryOption.equals("Bottoms")){
						//Hips(Inches) Better For Fitting Your Size			
						ProductOption hipsOption = new ProductOption();
						hipsOption.setCustom_option_title("Hips(Inches) Better For Fitting Your Size");
						hipsOption.setCustom_option_type("field");
						hipsOption.setCustom_option_is_required("0");
						hipsOption.setCustom_option_sort_order("5");
						hipsOption.setProduct(product);
						productOptionList.add(hipsOption);
					}
															
					heightOption.setProduct(product);
					weightOption.setProduct(product);
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
					
					String categoryOption = DataDictionary.getCategoryOption(this.shop.getCategory());
					if(categoryOption.equals("Tops")){
						//Bust(Inches) Better For Fitting Your Size			
						ProductOption bustOption = new ProductOption();
						bustOption.setCustom_option_title("Bust(Inches) Better For Fitting Your Size");
						bustOption.setCustom_option_type("field");
						bustOption.setCustom_option_is_required("0");
						bustOption.setCustom_option_sort_order("4");
						bustOption.setProduct(product);
						productOptionList.add(bustOption);
					}else if(categoryOption.equals("Bottoms")){
						//Hips(Inches) Better For Fitting Your Size			
						ProductOption hipsOption = new ProductOption();
						hipsOption.setCustom_option_title("Hips(Inches) Better For Fitting Your Size");
						hipsOption.setCustom_option_type("field");
						hipsOption.setCustom_option_is_required("0");
						hipsOption.setCustom_option_sort_order("4");
						hipsOption.setProduct(product);
						productOptionList.add(hipsOption);
					}
					
					heightOption.setProduct(product);
					weightOption.setProduct(product);
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

					if(elementStr.startsWith("风格")){
						elementSb.append("<li><stong>Style :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String styleValue = DataDictionary.getStyleValue(elementStrValue);
						elementSb.append(styleValue);
						elementSb.append("</i></li>");	
						product.setSb_style(styleValue);
					}
//					else if(elementStr.startsWith("款式")){
//						elementSb.append("<li><stong>Item :</stong>").append("<i>");
//						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
//						String itemValue = DataDictionary.getItemValue(elementStrValue);
//						elementSb.append(itemValue);
//						elementSb.append("</i></li>");	
//						product.setSb_item(itemValue);
//					}
					else if(elementStr.startsWith("袖长")){
						elementSb.append("<li><stong>Sleeve Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);	
						String sleeveLengthValue = DataDictionary.getSleeveLengthValue(elementStrValue);
						elementSb.append(sleeveLengthValue);
						elementSb.append("</i></li>");		
						product.setSb_sleeve_length(sleeveLengthValue);
					}else if(elementStr.startsWith("领子")||elementStr.startsWith("女装领型")){
						elementSb.append("<li><stong>Collar :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String necklineValue = DataDictionary.getNecklineValue(elementStrValue);
						elementSb.append(necklineValue);
						elementSb.append("</i></li>");		
						product.setSb_collar(necklineValue);
					}else if(elementStr.startsWith("裙长")){
						elementSb.append("<li><stong>Dresses Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String dressesLengthValue = DataDictionary.getDressesLengthValue(elementStrValue);
						elementSb.append(dressesLengthValue);
						elementSb.append("</i></li>");
						product.setSb_dresses_length(dressesLengthValue);
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
					}else if(elementStr.contains("腰")){
						elementSb.append("<li><stong>Waist Type :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String waistTypeValue = DataDictionary.getWaistTypeValue(elementStrValue);
						elementSb.append(waistTypeValue);
						elementSb.append("</i></li>");
						product.setSb_waist_type(waistTypeValue);
					}else if(elementStr.startsWith("裤长:")){
						elementSb.append("<li><stong>Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String lengthValue = DataDictionary.getLengthValue(elementStrValue);
						elementSb.append(lengthValue);
						elementSb.append("</i></li>");
						product.setSb_length(lengthValue);
					}
				}
				elementSb.append("</ul>");
				String productDescription = elementSb.toString();
				//productDescription = super.translateEnToCinese(productDescription);
				productDescription = productDescription.replaceAll("\r|\n", "").replaceAll(",", " ");
				
				///抓取特定属性
				if(shop.getSb_types()!=null&&!shop.getSb_types().equals("")){	
					product.setSb_types(shop.getSb_types());
				}else if(shop.getSb_style()!=null&&!shop.getSb_style().equals("")){
					product.setSb_style(shop.getSb_style());
				}else if(shop.getSb_item()!=null&&!shop.getSb_item().equals("")){
					product.setSb_item(shop.getSb_item());
				}else if(shop.getSb_sleeve_length()!=null&&!shop.getSb_sleeve_length().equals("")){	
					product.setSb_sleeve_length(shop.getSb_sleeve_length());
				}else if(shop.getSb_neckline()!=null&&!shop.getSb_neckline().equals("")){	
					product.setSb_neckline(shop.getSb_neckline());
				}else if(shop.getSb_dresses_length()!=null&&!shop.getSb_dresses_length().equals("")){
					product.setSb_dresses_length(shop.getSb_dresses_length());
				}else if(shop.getSb_pattern_type()!=null&&!shop.getSb_pattern_type().equals("")){
					product.setSb_pattern_type(shop.getSb_pattern_type());
				}else if(shop.getSb_material()!=null&&!shop.getSb_material().equals("")){
					product.setSb_material(shop.getSb_material());
				}
				
				List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();
				
				StringBuilder descriptionSb = new StringBuilder();
				descriptionSb.append("<div>").append(productDescription);
				descriptionSb.append("</div>");
				descriptionSb.append("<div style='clear:both;'></div>");
				descriptionSb.append("<div>");
				int imgNum = 0;
				Element productImageE = doc.getElementById("J_UlThumb");	
				Elements productImages = productImageE.select("li");
				if (productImages != null) {
					
					for (int i = 0; i < productImages.size(); i++) {
						ProductMedia productMedia = new ProductMedia();
						Element productImage = productImages.get(i);
						String imageUrlTemp = productImage.select("img").first().attr("src");
						String imageUrl = imageUrlTemp.replaceAll("60x60", "460x460");


						if (i == 0) {
							product.setImage("/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
							product.setSmall_image("/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
							product.setThumbnail("/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
						}
						productMedia.setMedia_image("/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
								+ ".jpg");
						productMedia.setMedia_position((i + 1) + "");
						productMedia.setProduct(product);
						productMedia.setMedia_attribute_id("703");
						productMedia.setMedia_is_disabled("0");
						ProductMediaList.add(productMedia);
						
						//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
						//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
						super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
						descriptionSb.append("<img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (i + 1)+ ".jpg'}}' /><br/><br/>");
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
							descriptionSb.append("<div>");				
							ProductMedia productMedia = new ProductMedia();
							Element productImage = productColorImages.get(i);
							String imageStr= productImage.attr("style");
							String imageUrl = imageStr.substring(imageStr.indexOf("(")+1, imageStr.indexOf(")")).replaceAll("30x30", "460x460");
	
							productMedia.setMedia_image("/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (imgNum + 1)+ ".jpg");
							productMedia.setMedia_position((imgNum + 1) + "");
							productMedia.setProduct(product);
							productMedia.setMedia_attribute_id("703");
							productMedia.setMedia_is_disabled("0");
							ProductMediaList.add(productMedia);
							
							//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
							//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
							super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+product.getSku() + "-"+ (imgNum + 1)+ ".jpg");
							descriptionSb.append("<img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (imgNum + 1)+ ".jpg'}}' />");
							descriptionSb.append("<br></br><table border='0' width='50%'><tr><td style='text-align:center;' ><stong>"+productColorArray[i]+"</stong></td></tr></table><br></br>");
							descriptionSb.append("</div>");
							imgNum++;
						}
						
					}
				}
				String categoryOption = DataDictionary.getCategoryOption(this.shop.getCategory());
				if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/size.jpg'}}'\"/></div>");													
				}else if(categoryOption.equals("Shoes")){
					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/shoes-size.jpg'}}'\"/></div>");													
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
	
//	解析单个product 信息
	public Product parseProduct(String productUrl,Product product) {
		System.out.println("productUrl--------------" + productUrl);
		try {
			
			//String productSku = productId;
			//随机生成50到200随机数作为库存
			Long stock =  Math.round(Math.random()*(200-50)+50);
			product.setQty(stock.toString());
			product.setStock(1);
			product.setIsExport(0);
			product.setSaleCount(0);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setUpdated_at(format.format(new Date()));
			product.setSb_shop(productUrl);		
			product.setMedia_gallery("Media_gallery");
			String weight = DataDictionary.getWeight(product.getCategory().replaceAll(" ", "_"));
			product.setWeight(weight);
			
			
			
			//org.jsoup.nodes.Document doc = Jsoup.connect(productUrl).get();			
			String content = super.doGet(productUrl);
			Document doc = Jsoup.parse(content);
			if(0==0){				
				Element productIdE = doc.getElementById("LineZing"); 
				String productSku =productIdE.attr("itemid");
				product.setProductSku(productSku);
				
//				Elements productPriceE = doc.getElementsByClass("J_originalPrice"); 
//				String productPrice =productPriceE.first().text();
				product.setPrice("000");
				
				
				//获得sku序号,设置sku
				Config config = new Config();
				config.setConfigName("skuNumber");
				List<Config> configs = tianmao.configService.queryConfig(config);
				if(configs!=null&&configs.size()>0){
					Config con = configs.get(0);
					Long skuNumber = Long.valueOf(con.getConfigValue())+1;
					System.out.println("product---skuNumber------"+skuNumber);						
					
					product.setSku("SKU"+skuNumber.toString());
					
					//保存新的sku序号
					con.setConfigValue(skuNumber.toString());
					tianmao.configService.update(con);				
				}else{
					product = null;
					return product;
				}
				
				Elements productNames = doc.getElementsByClass("tb-detail-hd");
				String productName = productNames.first().select("a").first().text();
					
				System.out.println("---------chineseName----------------"+productName);
				product.setChineseName(productName);			
				String translateName = translateEnToCineseBing(DataDictionary.trimProductName(productName));
				System.out.println("-------translate--englishName----------------"+translateName);
				
				StringBuilder englishNameB = new StringBuilder();
				//数据字典
				String[] nameArray = translateName.split(" ");
				if(nameArray!=null){
					for(int i = 0;i<nameArray.length;i++){
						String nameDict = nameArray[i];
						Dict dict = new Dict();
						dict.setName(nameDict);
						List<Dict> dicts = tianmao.dictService.queryDict(dict);
						if(dicts.size()>0){			
							//保存数据字典
							if(englishNameB.indexOf(nameDict)<0){
								englishNameB.append(nameDict).append(" ");
							}
						}else{
							continue;
						}
					}
				}				
				
				System.out.println("---------englishName----------------"+englishNameB.toString());
				product.setEnglishName(englishNameB.toString());

			
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
						
						optionValue = DataDictionary.getSizeValue(optionValue);
						System.out.println("size--------------" + optionValue);
						if(optionValue!=null&&!optionValue.equals("")){
							ProductOptionRow productOptionRow = new ProductOptionRow();
							if(optionValue.toLowerCase().contains("one")||optionValue.toLowerCase().contains("free")){
								product.setIsFreeSize(1);
							}else{
								product.setIsFreeSize(0);								
							}
							productOptionRow.setCustom_option_row_title(optionValue);
							productOptionRow.setCustom_option_row_sort(i + "");
							productOptionRow.setProductOption(sizeOption);
							productOptionRowList.add(productOptionRow);
							i++;
						}						
					}
					sizeOption.setOption_rows(productOptionRowList);
				}
				sizeOption.setProduct(product);
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
								productOptionRow.setProductOption(colorOption);
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								productColorSB.append(optionValue).append(";");
								i++;
							}
						}
						colorOption.setProduct(product);
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
					
					String categoryOption = DataDictionary.getCategoryOption(product.getCategory());
					if(categoryOption.equals("Tops")){
						//Bust(Inches) Better For Fitting Your Size			
						ProductOption bustOption = new ProductOption();
						bustOption.setCustom_option_title("Bust(Inches) Better For Fitting Your Size");
						bustOption.setCustom_option_type("field");
						bustOption.setCustom_option_is_required("0");
						bustOption.setCustom_option_sort_order("5");
						bustOption.setProduct(product);
						productOptionList.add(bustOption);
					}else if(categoryOption.equals("Bottoms")){
						//Hips(Inches) Better For Fitting Your Size			
						ProductOption hipsOption = new ProductOption();
						hipsOption.setCustom_option_title("Hips(Inches) Better For Fitting Your Size");
						hipsOption.setCustom_option_type("field");
						hipsOption.setCustom_option_is_required("0");
						hipsOption.setCustom_option_sort_order("5");
						hipsOption.setProduct(product);
						productOptionList.add(hipsOption);
					}
															
					heightOption.setProduct(product);
					weightOption.setProduct(product);
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
					
					String categoryOption = DataDictionary.getCategoryOption(product.getCategory());
					if(categoryOption.equals("Tops")){
						//Bust(Inches) Better For Fitting Your Size			
						ProductOption bustOption = new ProductOption();
						bustOption.setCustom_option_title("Bust(Inches) Better For Fitting Your Size");
						bustOption.setCustom_option_type("field");
						bustOption.setCustom_option_is_required("0");
						bustOption.setCustom_option_sort_order("4");
						bustOption.setProduct(product);
						productOptionList.add(bustOption);
					}else if(categoryOption.equals("Bottoms")){
						//Hips(Inches) Better For Fitting Your Size			
						ProductOption hipsOption = new ProductOption();
						hipsOption.setCustom_option_title("Hips(Inches) Better For Fitting Your Size");
						hipsOption.setCustom_option_type("field");
						hipsOption.setCustom_option_is_required("0");
						hipsOption.setCustom_option_sort_order("4");
						hipsOption.setProduct(product);
						productOptionList.add(hipsOption);
					}
					
					heightOption.setProduct(product);
					weightOption.setProduct(product);
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
						product.setSb_collar(necklineValue);
					}else if(elementStr.startsWith("裙长")){
						elementSb.append("<li><stong>Dresses Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String dressesLengthValue = DataDictionary.getDressesLengthValue(elementStrValue);
						elementSb.append(dressesLengthValue);
						elementSb.append("</i></li>");
						product.setSb_dresses_length(dressesLengthValue);
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
					}else if(elementStr.contains("腰")){
						elementSb.append("<li><stong>Waist Type :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String waistTypeValue = DataDictionary.getWaistTypeValue(elementStrValue);
						elementSb.append(waistTypeValue);
						elementSb.append("</i></li>");
						product.setSb_waist_type(waistTypeValue);
					}else if(elementStr.startsWith("裤长:")){
						elementSb.append("<li><stong>Length :</stong>").append("<i>");
						String elementStrValue = elementStr.substring(elementStr.indexOf(":")+2);
						String lengthValue = DataDictionary.getLengthValue(elementStrValue);
						elementSb.append(lengthValue);
						elementSb.append("</i></li>");
						product.setSb_length(lengthValue);
					}
				}
				elementSb.append("</ul>");
				String productDescription = elementSb.toString();
				//productDescription = super.translateEnToCinese(productDescription);
				productDescription = productDescription.replaceAll("\r|\n", "").replaceAll(",", " ");
				
				
				List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();
				
				StringBuilder descriptionSb = new StringBuilder();
				descriptionSb.append("<div>").append(productDescription);
				descriptionSb.append("</div>");
				descriptionSb.append("<div style='clear:both;'></div>");
				descriptionSb.append("<div>");
				int imgNum = 0;
				Element productImageE = doc.getElementById("J_UlThumb");	
				Elements productImages = productImageE.select("li");
				if (productImages != null) {
					
					for (int i = 0; i < productImages.size(); i++) {
						ProductMedia productMedia = new ProductMedia();
						Element productImage = productImages.get(i);
						String imageUrlTemp = productImage.attr("style");
						String imageUrl = imageUrlTemp.substring(imageUrlTemp.indexOf("(")+1, imageUrlTemp.lastIndexOf(")")).replaceAll("60x60", "460x460");

						if (i == 0) {
							product.setImage("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
							product.setSmall_image("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
							product.setThumbnail("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
									+ ".jpg");
						}
						productMedia.setMedia_image("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (i + 1)
								+ ".jpg");
						productMedia.setMedia_position((i + 1) + "");
						productMedia.setProduct(product);
						productMedia.setMedia_attribute_id("703");
						productMedia.setMedia_is_disabled("0");
						ProductMediaList.add(productMedia);
						
						//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
						//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
						super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
						descriptionSb.append("<img src='{{media url='upload/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (i + 1)+ ".jpg'}}' /><br/><br/>");
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
							descriptionSb.append("<div>");				
							ProductMedia productMedia = new ProductMedia();
							Element productImage = productColorImages.get(i);
							String imageStr= productImage.attr("style");
							String imageUrl = imageStr.substring(imageStr.indexOf("(")+1, imageStr.indexOf(")")).replaceAll("30x30", "460x460");
	
							productMedia.setMedia_image("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (imgNum + 1)+ ".jpg");
							productMedia.setMedia_position((imgNum + 1) + "");
							productMedia.setProduct(product);
							productMedia.setMedia_attribute_id("703");
							productMedia.setMedia_is_disabled("0");
							ProductMediaList.add(productMedia);
							
							//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
							//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
							super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+product.getSku() + "-"+ (imgNum + 1)+ ".jpg");
							descriptionSb.append("<img src='{{media url='upload/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (imgNum + 1)+ ".jpg'}}' />");
							descriptionSb.append("<br></br><table border='0' width='50%'><tr><td style='text-align:center;' ><stong>"+productColorArray[i]+"</stong></td></tr></table><br></br>");
							descriptionSb.append("</div>");
							imgNum++;
						}
						
					}
				}
				String categoryOption = DataDictionary.getCategoryOption(product.getCategory());
				if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/size.jpg'}}'\"/></div>");													
				}else if(categoryOption.equals("Shoes")){
					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/shoes-size.jpg'}}'\"/></div>");													
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
}