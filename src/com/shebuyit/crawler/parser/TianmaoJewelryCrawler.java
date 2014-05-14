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

public class TianmaoJewelryCrawler extends Crawler {
		
	private IProductService productService;
	
	private IShopService shopService;
	
	private IConfigService configService;
	
	private IDictService dictService;
			
	private static TianmaoJewelryCrawler tianmaoJewelry;
	
		
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

		tianmaoJewelry = this;

		tianmaoJewelry.productService = this.productService;
		
		tianmaoJewelry.configService = this.configService;
		
		tianmaoJewelry.shopService = this.shopService;

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
					String prouductOrlPriceStr = ele.select("strong").first().text();	
					if(prouductOrlPriceStr!=null&&!prouductOrlPriceStr.equals("")){
						String productSpecialPriceE = ele.select("div").get(3)==null?null:ele.select("div").get(3).toString();	
						String productSpecialPriceStr = "";
						if(productSpecialPriceE!=null){							
							productSpecialPriceStr = productSpecialPriceE.substring(productSpecialPriceE.indexOf("折扣价是")+5, productSpecialPriceE.indexOf("原价是")-1);
						}
						double prouductOrlPrice = Double.valueOf(prouductOrlPriceStr);
						double prouductSpecialPrice;
						if(!productSpecialPriceStr.equals("")){
							prouductSpecialPrice = Double.valueOf(productSpecialPriceStr);
						}else{
							prouductSpecialPrice = prouductOrlPrice;
						}
						
						
						double priceMax = DataDictionary.getPriceMax(this.shop.getCategory().replaceAll(" ", "_"));
						if(prouductSpecialPrice<priceMax){
							Product product = new Product();
							
							//判断product是否已经存在
							Product existProduct = new Product();
							existProduct.setProductSku(productId);
							existProduct.setShopCanal("tianmao");
							List<Product> existProducts = tianmaoJewelry.productService.queryProduct(existProduct);
							if(existProducts!=null&&existProducts.size()>0){
								System.out.println("product---------"+productId+"----------------------exist-----------------------------------");						
								continue;
							}
							
							//获得sku序号,设置sku
							Config config = new Config();
							config.setConfigName("skuNumber");
							List<Config> configs = tianmaoJewelry.configService.queryConfig(config);
							if(configs!=null&&configs.size()>0){
								Config con = configs.get(0);
								Long skuNumber = Long.valueOf(con.getConfigValue())+1;
								System.out.println("product---skuNumber------"+skuNumber);						
								
								product.setSku("SKU"+skuNumber.toString());
								
								//保存新的sku序号
								con.setConfigValue(skuNumber.toString());
								tianmaoJewelry.configService.update(con);				
							}else{
								continue;
							}	
							
							double expressFee = DataDictionary.getExpressFee(this.shop.getCategory().replaceAll(" ", "_"));							
									
							if(prouductSpecialPrice<=50){
								double special_price = (prouductSpecialPrice*2.2+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(prouductSpecialPrice>50&&prouductSpecialPrice<=100){
								double special_price = (prouductSpecialPrice*2+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(prouductSpecialPrice>100&&prouductSpecialPrice<=130){
								double special_price = (prouductSpecialPrice*1.9+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(prouductSpecialPrice>130&&prouductSpecialPrice<=160){
								double special_price = (prouductSpecialPrice*1.8+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(prouductSpecialPrice>160&&prouductSpecialPrice<=200){
								double special_price = (prouductSpecialPrice*1.7+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}else if(prouductSpecialPrice>200){
								double special_price = (prouductSpecialPrice*1.6+expressFee)/DataDictionary.getDollarRate();	
								product.setSpecial_price(String.format("%.2f",special_price));
								product.setPrice(String.format("%.2f",special_price*1.5));
							}
							product.setOrl_price(String.format("%.2f",prouductSpecialPrice));	
													

							product = parseProductInfoUrl(productUrl,productId,product);	
							if(product!=null){
								tianmaoJewelry.productService.save(product);
								System.out.println(productNumber+"---------productNumber--------------------------------------------------------------");
								System.out.println("---------prouductPrice----------------"+prouductSpecialPrice);
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
	public void parseProductImage(String productUrl,Product product) {
		System.out.println("productUrl--------------" + productUrl);
		try {
			Thread.sleep(30000);
					
			//org.jsoup.nodes.Document doc = Jsoup.connect(productUrl).get();			
			String content = super.doGet(productUrl);
			Document doc = Jsoup.parse(content);			
			if(0==0){	
				int imgNum = 0;
				Elements productImages = doc.getElementsByClass("tb-s40");		
				if (productImages != null) {
					
					for (int i = 0; i < productImages.size(); i++) {
						Element productImage = productImages.get(i);
						String imageUrl = productImage.select("img").first().absUrl("src").replaceAll("40x40", "460x460");			
						super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/tm/"+product.getShopSku()+"/"+product.getCategory()+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
						imgNum = i+1;
					}				
				}
				
		
				Elements productColors = doc.getElementsByClass("tb-img");
				if(productColors != null){
					Elements productColorImages	 = productColors.first().select("a");
					if (productColorImages != null) {
						
						for (int i = 0; i < productColorImages.size(); i++) {
										
							Element productImage = productColorImages.get(i);
							String imageStr= productImage.attr("style");
							if(imageStr!=null&&!imageStr.equals("")){	
								String imageUrl = imageStr.substring(imageStr.indexOf("(")+1, imageStr.indexOf(")")).replaceAll("30x30", "460x460");
											
								super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/tm/"+product.getShopSku()+"/"+product.getCategory()+"/"+product.getSku() + "-"+ (imgNum + 1)+ ".jpg");
								imgNum++;
							}
							
						}
						
					}
				}

				
				}
			} catch (Exception e) {
				e.printStackTrace();
				product = null;
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
				String productName = productNames.first().select("a").first().text();
				
				for(String shopNameStr:this.shop.getShopName().split(";")){
					productName = productName.replaceAll(shopNameStr, "");
				}	
				System.out.println("---------chineseName----------------"+productName);
				product.setChineseName(productName);			
				String translateName = translateEnToCineseBing(DataDictionary.trimProductName(productName));
				System.out.println("-------translate--englishName----------------"+translateName);
				
				StringBuilder englishNameB = new StringBuilder();
				//数据字典
				translateName = translateName.replaceAll("'", "");
				String[] nameArray = translateName.split(" ");
				if(nameArray!=null){
					for(int i = 0;i<nameArray.length;i++){
						String nameDict = nameArray[i];
						Dict dict = new Dict();
						dict.setName(nameDict);
						List<Dict> dicts = tianmaoJewelry.dictService.queryDict(dict);
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
//				ProductOption sizeOption = new ProductOption();
//				Elements optionSizeClass = doc.getElementsByClass("J_TSaleProp");
//				Elements optionSizes = optionSizeClass.first().select("span");
//				if (optionSizes != null && optionSizes.size() > 0) {
//					product.setHas_options("1");
//					product.setRequired_options("1");
//					List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();
//
//					sizeOption.setCustom_option_title("Size");
//					sizeOption.setCustom_option_type("drop_down");
//					sizeOption.setCustom_option_is_required("1");
//					sizeOption.setCustom_option_sort_order("1");
//
//					Iterator optionSizeIterator = optionSizes.iterator();
//					int i = 1;
//					while (optionSizeIterator.hasNext()) {
//						Element option = (Element) optionSizeIterator.next();
//						String optionValue = option.text();						
//						
//						optionValue = DataDictionary.getSizeValue(optionValue);
//						System.out.println("size--------------" + optionValue);
//						if(optionValue!=null&&!optionValue.equals("")){
//							ProductOptionRow productOptionRow = new ProductOptionRow();
//							productOptionRow.setCustom_option_row_title(optionValue);
//							productOptionRow.setCustom_option_row_sort(i + "");
//							productOptionRow.setProductOption(sizeOption);
//							productOptionRowList.add(productOptionRow);
//							i++;
//						}						
//					}
//					sizeOption.setOption_rows(productOptionRowList);
//				}
//				sizeOption.setProduct(product);
//				productOptionList.add(sizeOption);
				
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
					productOptionList.add(colorOption);
				}				
				product.setProduct_options(productOptionList);
				

				StringBuilder elementSb = new StringBuilder();
				elementSb.append("<ul>");
				Element productDescriptionE = doc.getElementById("J_AttrList"); 
				Elements descriptionEs = productDescriptionE.select("li");
				for (Element element : descriptionEs) {
					String elementStr = element.text();

					if(elementStr.contains("材质")||elementStr.contains("样式")||elementStr.contains("延长链")||elementStr.contains("颜色")||elementStr.contains("形状")||elementStr.contains("风格")||elementStr.contains("镶嵌")||elementStr.contains("带坠")||elementStr.contains("链周长")){
						System.out.println("---------element----------------"+elementStr);		
						String translateElementName = translateEnToCineseBing(elementStr);
						System.out.println("-------translate--element----------------"+translateElementName);
						String[] translateElementNames = translateElementName.split(":");
						elementSb.append("<li><span>");
						elementSb.append(translateElementNames[0]);
						elementSb.append(" :</span><i>");
						elementSb.append(translateElementNames[1]);
						elementSb.append("</i></li>");		
					}
				}
				elementSb.append("</ul>");
				String productDescription = elementSb.toString();
				//productDescription = super.translateEnToCinese(productDescription);
				productDescription = productDescription.replaceAll("\r|\n", "").replaceAll(",", " ");
				
//				///抓取特定属性
//				if(shop.getSb_types()!=null){	
//					product.setSb_types(shop.getSb_types());
//				}else if(shop.getSb_style()!=null){
//					product.setSb_style(shop.getSb_style());
//				}else if(shop.getSb_item()!=null){
//					product.setSb_item(shop.getSb_item());
//				}else if(shop.getSb_sleeve_length()!=null){	
//					product.setSb_sleeve_length(shop.getSb_sleeve_length());
//				}else if(shop.getSb_neckline()!=null){	
//					product.setSb_neckline(shop.getSb_neckline());
//				}else if(shop.getSb_dresses_length()!=null){
//					product.setSb_dresses_length(shop.getSb_dresses_length());
//				}else if(shop.getSb_pattern_type()!=null){
//					product.setSb_pattern_type(shop.getSb_pattern_type());
//				}
				
				List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();
				
				StringBuilder descriptionSb = new StringBuilder();
				descriptionSb.append("<div>").append(productDescription);
				descriptionSb.append("</div>");
				descriptionSb.append("<div style='clear:both;'></div>");
				descriptionSb.append("<div>");
				int imgNum = 0;
				Elements productImages = doc.getElementsByClass("tb-s40");		
				if (productImages != null) {
					
					for (int i = 0; i < productImages.size(); i++) {
						ProductMedia productMedia = new ProductMedia();
						Element productImage = productImages.get(i);
						String imageUrl = productImage.select("img").first().absUrl("src").replaceAll("40x40", "460x460");

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
										
							ProductMedia productMedia = new ProductMedia();
							Element productImage = productColorImages.get(i);
							String imageStr= productImage.attr("style");
							if(imageStr!=null&&!imageStr.equals("")){
								descriptionSb.append("<div>");	
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
				}
//				String categoryOption = DataDictionary.getCategoryOption(this.shop.getCategory());
//				if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
//					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/size.jpg'}}'\"/></div>");													
//				}else if(categoryOption.equals("Shoes")){
//					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/shoes-size.jpg'}}'\"/></div>");													
//				}
//				
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
			Thread.sleep(30000);
			
			//随机生成50到200随机数作为库存
			Long stock =  Math.round(Math.random()*(200-50)+50);
			product.setQty(stock.toString());
			product.setStock(1);
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
				List<Config> configs = tianmaoJewelry.configService.queryConfig(config);
				if(configs!=null&&configs.size()>0){
					Config con = configs.get(0);
					Long skuNumber = Long.valueOf(con.getConfigValue())+1;
					System.out.println("product---skuNumber------"+skuNumber);						
					
					product.setSku("SKU"+skuNumber.toString());
					
					//保存新的sku序号
					con.setConfigValue(skuNumber.toString());
					tianmaoJewelry.configService.update(con);				
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
				translateName = translateName.replaceAll("'", "");
				String[] nameArray = translateName.split(" ");
				if(nameArray!=null){
					for(int i = 0;i<nameArray.length;i++){
						String nameDict = nameArray[i];
						Dict dict = new Dict();
						dict.setName(nameDict);
						List<Dict> dicts = tianmaoJewelry.dictService.queryDict(dict);
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
					productOptionList.add(colorOption);
				}				
				product.setProduct_options(productOptionList);
				

				StringBuilder elementSb = new StringBuilder();
				elementSb.append("<ul>");
				Element productDescriptionE = doc.getElementById("J_AttrList"); 
				Elements descriptionEs = productDescriptionE.select("li");
				for (Element element : descriptionEs) {
					String elementStr = element.text();

					if(elementStr.contains("材质")||elementStr.contains("样式")||elementStr.contains("延长链")||elementStr.contains("颜色")||elementStr.contains("形状")||elementStr.contains("风格")||elementStr.contains("镶嵌")||elementStr.contains("带坠")||elementStr.contains("链周长")){
						System.out.println("---------element----------------"+elementStr);		
						String translateElementName = translateEnToCineseBing(elementStr);
						System.out.println("-------translate--element----------------"+translateElementName);
						String[] translateElementNames = translateElementName.split(":");
						elementSb.append("<li><span>");
						elementSb.append(translateElementNames[0]);
						elementSb.append(" :</span><i>");
						elementSb.append(translateElementNames[1]);
						elementSb.append("</i></li>");		
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
				Elements productImages = doc.getElementsByClass("tb-s40");		
				if (productImages != null) {
					
					for (int i = 0; i < productImages.size(); i++) {
						ProductMedia productMedia = new ProductMedia();
						Element productImage = productImages.get(i);
						String imageUrl = productImage.select("img").first().absUrl("src").replaceAll("40x40", "460x460");

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
										
							ProductMedia productMedia = new ProductMedia();
							Element productImage = productColorImages.get(i);
							String imageStr= productImage.attr("style");
							if(imageStr!=null&&!imageStr.equals("")){
								descriptionSb.append("<div>");	
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
				}
//				String categoryOption = DataDictionary.getCategoryOption(this.shop.getCategory());
//				if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
//					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/size.jpg'}}'\"/></div>");													
//				}else if(categoryOption.equals("Shoes")){
//					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/tm/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-size.jpg'}}' onerror=\"javascript:this.src='{{media url='upload/home/shoes-size.jpg'}}'\"/></div>");													
//				}
//				
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