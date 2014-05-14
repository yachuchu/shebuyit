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

public class SammydressCrawler extends Crawler{
	
	private IProductService productService;
	
	private IShopService shopService;
	
	private IConfigService configService;
		
	private IDictService dictService;
	
	private static SammydressCrawler sammydress;
	
	
	
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

		sammydress = this;

		sammydress.productService = this.productService;
		
		sammydress.configService = this.configService;
		
		sammydress.shopService = this.shopService;
		
		sammydress.dictService = this.dictService;

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
		String tempPageURL = indexURL.replaceAll(".html", "-page-%p.html");
		int pageCount = getPageCount(indexURL);
		for (int i = 1; i < pageCount+1; i++) {
				String pageUrl = tempPageURL.replaceAll("%p", i + "");
				start(pageUrl);
		}
		System.out.println("------Parser---End---------");
					
	}
	
	public int getPageCount(String url) {
		int pageCount = 1;
		try {
			
//			org.jsoup.nodes.Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:5.0)").timeout(1000).get();
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			
			Element pageSelect = doc.getElementById("PB_Page_Select");
			if (pageSelect != null) {
				Elements pageOptions = pageSelect.select("option");
				String pageNumber = pageOptions.last().text();
				pageCount = Integer.parseInt(pageNumber.trim());
				System.out.println("---total------pageCount--------" + pageCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageCount;
	}
	
	
	public void start(String url) {
		try {
			Thread.sleep(30000);
//			org.jsoup.nodes.Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:5.0)").get();
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			Elements listProducts = doc.getElementsByClass("pList2_name");
			for (Element ele : listProducts) {
				String productUrl = ele.select("a").attr("href");		
				Product product = parseProductInfoUrl(productUrl);
				if(product!=null){
//					/productList.add(product);
					sammydress.productService.save(product);
					System.out.println(productNumber+"---------productNumber--------------------------------------------------------------");
					productNumber++;	
				}												
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	//解析product
	public Product parseProductInfoUrl(String url) {
		
		System.out.println("productUrl--------------" + url);
		Product product = null;
		try {
			Thread.sleep(10000);
//			org.jsoup.nodes.Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:5.0)").get();
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			product = new Product();
			Element productSKUE = doc.getElementById("title_goodSN");
			String productSku = productSKUE.text();
			product.setProductSku(productSku);
			
			//判断product是否已经存在
			Product existProduct = new Product();
			existProduct.setProductSku(productSku);
			List<Product> existProducts = sammydress.productService.queryProduct(existProduct);
			if(existProducts!=null&&existProducts.size()>0){
				
				System.out.println("product---------"+product.getProductSku()+"----------------------exist-----------------------------------");						
				product = null;
				return product;
			}
			
			//获得sku序号,设置sku
			Config config = new Config();
			config.setConfigName("sammydressSku");
			List<Config> configs = sammydress.configService.queryConfig(config);
			if(configs!=null&&configs.size()>0){
				Config con = configs.get(0);
				Long skuNumber = Long.valueOf(con.getConfigValue())+1;
				System.out.println("product---sammydressSku------"+skuNumber);						
				
				product.setSku("SKU"+skuNumber.toString());
				
				//保存新的sku序号
				con.setConfigValue(skuNumber.toString());
				sammydress.configService.update(con);				
			}else{
				product = null;
				return product;
			}
						
			
			Long stock =  Math.round(Math.random()*(200-50)+50);
			product.setQty(stock.toString());
			product.setStock(1);
			product.setIsExport(0);
			product.setSaleCount(0);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setCategory(this.shop.getCategory());
			product.setAttribute_set(this.shop.getAttribute_set());
			product.setSb_shop(url);
			product.setShopSku(this.shop.getShopSku());
			//product.setSb_brand(shopBrand);
			String weight = DataDictionary.getWeight(this.shop.getCategory().replaceAll(" ", "_"));
			product.setWeight(weight);
			
			Element productNameE = doc.getElementById("h1_goodsTitle");
			String productName = productNameE.select("h1").text();
			product.setEnglishName(productName);
			
			//数据字典
//			String[] nameArray = productName.split(" ");
//			if(nameArray!=null){
//				for(int i = 0;i<nameArray.length;i++){
//					String nameDict = nameArray[i];
//					Dict dict = new Dict();
//					dict.setName(nameDict);
//					List<Dict> dicts = sammydress.dictService.queryDict(dict);
//					if(dicts==null||dicts.size()==0){			
//						//保存数据字典
//						dict.setCode(nameDict);
//						dict.setCreated_time(format.format(new Date()));
//						sammydress.dictService.save(dict);				
//					}else{
//						continue;
//					}
//				}
//			}
			
			
			
			Element productSpecialPriceE = doc.getElementById("unit_price");
			if(productSpecialPriceE!=null){
			String productSpecialPriceStr = productSpecialPriceE.text();
			if(productSpecialPriceStr!=null&&!productSpecialPriceStr.equals("")){	
				double expressFee = DataDictionary.getExpressFee(this.shop.getCategory().replaceAll(" ", "_"));	
				
				double productSpecialPrice = Double.parseDouble(productSpecialPriceStr)*DataDictionary.getDollarRate()*0.9;
				
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
				
				product.setOrl_price(productSpecialPriceStr);
				
				}
			}		
			
			if(product.getPrice()==null||product.getPrice().equals("")){
				product = null;
				return product;
			}

			//System.out.println("productPrice--------------" + product.getPrice());

			List<ProductOption> productOptionList = new ArrayList<ProductOption>();
			
			Element productOption0E = doc.getElementById("select_same_goods_id_0"); 
			String option0Str = productOption0E.select("label").text();
			if(option0Str!=null&&option0Str.equals("Size:")){
				ProductOption sizeOption = new ProductOption();
				Elements optionSizes = productOption0E.select("option");
				if (optionSizes != null && optionSizes.size() > 0) {
					List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

					sizeOption.setCustom_option_title("Size");
					sizeOption.setCustom_option_type("drop_down");
					sizeOption.setCustom_option_is_required("1");
					sizeOption.setCustom_option_sort_order("1");

					Iterator optionIterator = optionSizes.iterator();
					int i = 1;
					while (optionIterator.hasNext()) {
						Element option = (Element) optionIterator.next();
						String optionValue = option.text();
						if (optionValue.startsWith("Please")) {
							continue;
						} else {
							ProductOptionRow productOptionRow = new ProductOptionRow();
							if(optionValue.toLowerCase().contains("one")||optionValue.toLowerCase().contains("free")){
								productOptionRow.setCustom_option_row_title("Free Size");
								product.setIsFreeSize(1);
							}else{
								productOptionRow.setCustom_option_row_title(optionValue);
							}
							
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
			}else if(option0Str!=null&&option0Str.equals("Color:")){
				ProductOption colorOption = new ProductOption();
				Elements optionColors = productOption0E.select("option");
				if (optionColors != null && optionColors.size() > 0) {
					List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

					colorOption.setCustom_option_title("Color");
					colorOption.setCustom_option_type("drop_down");
					colorOption.setCustom_option_is_required("1");
					colorOption.setCustom_option_sort_order("1");

					Iterator optionColorIterator = optionColors.iterator();
					int i = 1;
					while (optionColorIterator.hasNext()) {
						
						Element option = (Element) optionColorIterator.next();
						String optionValue = option.text();						
						if (optionValue.startsWith("Please")) {
							continue;
						} else {					
							optionValue = DataDictionary.getVanclColor(optionValue.toLowerCase());
							if(optionValue!=null&&!optionValue.equals("")){
								ProductOptionRow productOptionRow = new ProductOptionRow();								
								productOptionRow.setCustom_option_row_title(optionValue);
								productOptionRow.setCustom_option_row_sort(i + "");
								productOptionRow.setProductOption(colorOption);
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								i++;
							}
						}
					}
					colorOption.setOption_rows(productOptionRowList);
				}
				colorOption.setProduct(product);
				productOptionList.add(colorOption);
				
			}
			
			Element productOption1E = doc.getElementById("select_same_goods_id_1"); 
			String option1Str = productOption1E.select("label").text();
			if(option1Str!=null&&option1Str.equals("Color:")){
				ProductOption colorOption = new ProductOption();
				Elements optionColors = productOption1E.select("option");
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
											
						if (optionValue.startsWith("Please")) {
							continue;
						} else {					
							optionValue = DataDictionary.getVanclColor(optionValue.toLowerCase());
							if(optionValue!=null&&!optionValue.equals("")){
								ProductOptionRow productOptionRow = new ProductOptionRow();								
								productOptionRow.setCustom_option_row_title(optionValue);
								productOptionRow.setCustom_option_row_sort(i + "");
								productOptionRow.setProductOption(colorOption);
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								i++;
							}
						}
					}
					colorOption.setOption_rows(productOptionRowList);
				}
				colorOption.setProduct(product);
				productOptionList.add(colorOption);
				
			}
			
			
			
			
			
			
			
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
			
			product.setProduct_options(productOptionList);
			
			//Description
			StringBuilder descriptionSb = new StringBuilder();
			
			Elements productDetails = doc.getElementsByClass("xxkkk20");
			if(productDetails!=null&&productDetails.size()>0){
				String productDetail = productDetails.first().html();
				productDetail = productDetail.replaceAll("\r|\n", "").replaceAll(",", " ");				
				
				descriptionSb.append("<div>").append(productDetail).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			productDetails = doc.getElementsByClass("xxkkk03");
			if(productDetails!=null&&productDetails.size()>0){
				String productDetail = productDetails.first().html();
				productDetail = productDetail.replaceAll("\r|\n", "").replaceAll(",", " ");				
				
				descriptionSb.append("<div>").append(productDetail).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			
			
			Elements productInfos = doc.getElementsByClass("product_desc");
			if(productInfos!=null&&productInfos.size()>0){
				String productInfoStr = productInfos.first().html();
				String productInfo = productInfoStr.substring(0, productInfoStr.indexOf("</table>"))+"</table></div></div>";
				productInfo = productInfo.replaceAll("\r|\n", "").replaceAll(",", " ").replaceAll("<table border=\"0\"", "<table style=\"border-collapse:collapse;\" border=\"1\"").replaceAll("ffcccc", "666666").replaceAll("bgcolor=\"#cccccc\"", "").replaceAll("bgcolor=\"#999999\"", "");				
				
				descriptionSb.append("<div>").append(productInfo).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			Element productInfoSuojinE = doc.getElementById("suojin"); 
			if(productInfoSuojinE!=null){
				String productInfoSuojinStr = productInfoSuojinE.html();
				String productInfoSuojin = productInfoSuojinStr.substring(0, productInfoSuojinStr.indexOf("</table>"))+"</table></div>";
				productInfoSuojin = productInfoSuojin.replaceAll("\r|\n", "").replaceAll(",", " ");				
				
				descriptionSb.append("<div>").append(productInfoSuojin).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			
			List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();

			Elements productMedias = doc.getElementsByClass("pro_intr_show_text");
			Elements productImages = productMedias.first().select("img");
			if (productImages != null) {
				descriptionSb.append("<div style='clear:both;'></br></div><div>");
				int j = 0;
				for (int i = 0; i < productImages.size(); i++) {
					ProductMedia productMedia = new ProductMedia();
					Element productImage = productImages.get(i);
					String imageUrl = productImage.attr("src");
					if("http://cloud3.faout.com/uploads/201207/heditor/201207241028595507.jpg".equals(imageUrl.toLowerCase())
					 ||"http://cloud3.faout.com/uploads/201207/heditor/201207241630068451.jpg".equals(imageUrl.toLowerCase())
					 ||"http://cloud3.faout.com/uploads/201301/heditor/201301081051576004.jpg".equals(imageUrl.toLowerCase())
					 ||"http://cloud3.faout.com/uploads/201207/heditor/201207031646265253.jpg".equals(imageUrl.toLowerCase())
					 ||"http://cloud3.faout.com/uploads/201207/heditor/201207110856041942.jpg".equals(imageUrl.toLowerCase()))
						
					{
						continue;
					}					
					if (j == 0) {
						product.setImage("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
						product.setSmall_image("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
						product.setThumbnail("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
					}
					productMedia.setMedia_image("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
					productMedia.setMedia_position((j + 1) + "");
					productMedia.setProduct(product);
					productMedia.setMedia_attribute_id("703");
					productMedia.setMedia_is_disabled("0");
					ProductMediaList.add(productMedia);
					
					//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
					//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
					super.downloadFile(imageUrl, SystemConfig.Image_Path()+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+product.getSku() + "-"+ (j + 1)+ ".jpg");
					descriptionSb.append("<img src='{{media url='upload/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (j + 1)+ ".jpg'}}' /><br/><br/>");
					j++;

				}
				descriptionSb.append("</div>");
			}

			if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
				descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/home/size.jpg'}}'/></div>");													
			}else if(categoryOption.equals("Shoes")){
				descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/home/shoes-size.jpg'}}'/></div>");													
			}
			product.setDescription(descriptionSb.toString());
			product.setProduct_medias(ProductMediaList);
			

		} catch (Exception e) {
			System.out.println("------parse Product error:--"+e.getMessage());
			e.printStackTrace();
			product = null;
			return product;
			
		}		
		return product;
	}
	
	//解析单个product
	public Product parseProduct(String pageUrl,Product product) {
		System.out.println("productUrl--------------" + pageUrl);
		try {
			String content = super.doGet(pageUrl);
			Document doc = Jsoup.parse(content);
			Element productSKUE = doc.getElementById("productCodeSpan");
			String productSku = productSKUE.text();
			product.setProductSku(productSku);
			
			//判断product是否已经存在
			Product existProduct = new Product();
			existProduct.setProductSku(productSku);
			List<Product> existProducts = sammydress.productService.queryProduct(existProduct);
			if(existProducts!=null&&existProducts.size()>0){
				
				System.out.println("product---------"+product.getProductSku()+"----------------------exist-----------------------------------");						
				product = null;
				return product;
			}
			
			//获得sku序号,设置sku
			Config config = new Config();
			config.setConfigName("sammydressSku");
			List<Config> configs = sammydress.configService.queryConfig(config);
			if(configs!=null&&configs.size()>0){
				Config con = configs.get(0);
				Long skuNumber = Long.valueOf(con.getConfigValue())+1;
				System.out.println("product---sammydressSku------"+skuNumber);						
				
				product.setSku("SKU"+skuNumber.toString());
				
				//保存新的sku序号
				con.setConfigValue(skuNumber.toString());
				sammydress.configService.update(con);				
			}else{
				product = null;
				return product;
			}
						
			
			Long stock =  Math.round(Math.random()*(200-50)+50);
			product.setQty(stock.toString());
			product.setStock(1);
			product.setIsExport(0);
			product.setSaleCount(0);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setCategory(this.shop.getCategory());
			product.setAttribute_set(this.shop.getAttribute_set());
			product.setSb_shop(pageUrl);
			product.setShopSku(this.shop.getShopSku());
			//product.setSb_brand(shopBrand);
			String weight = DataDictionary.getWeight(this.shop.getCategory().replaceAll(" ", "_"));
			product.setWeight(weight);
			
			Element productNameE = doc.getElementById("h1_goodsTitle");
			String productName = productNameE.select("h1").text();
			product.setEnglishName(productName);
			
			//数据字典
			String[] nameArray = productName.split(" ");
			if(nameArray!=null){
				for(int i = 0;i<nameArray.length;i++){
					String nameDict = nameArray[i];
					Dict dict = new Dict();
					dict.setName(nameDict);
					List<Dict> dicts = sammydress.dictService.queryDict(dict);
					if(dicts==null||dicts.size()==0){			
						//保存数据字典
						dict.setCode(nameDict);
						dict.setCreated_time(format.format(new Date()));
						sammydress.dictService.save(dict);				
					}else{
						continue;
					}
				}
			}
			
			
			
			Element productSpecialPriceE = doc.getElementById("unit_price");
			if(productSpecialPriceE!=null){
			String productSpecialPriceStr = productSpecialPriceE.text();
			if(productSpecialPriceStr!=null&&!productSpecialPriceStr.equals("")){	
				double expressFee = DataDictionary.getExpressFee(this.shop.getCategory().replaceAll(" ", "_"));	
				
				double productSpecialPrice = Double.parseDouble(productSpecialPriceStr)*DataDictionary.getDollarRate();
				
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
				
				product.setOrl_price(productSpecialPriceStr);
				
				}
			}		
			
			if(product.getPrice()==null||product.getPrice().equals("")){
				product = null;
				return product;
			}

			//System.out.println("productPrice--------------" + product.getPrice());

			List<ProductOption> productOptionList = new ArrayList<ProductOption>();
			
			Element productOption0E = doc.getElementById("select_same_goods_id_0"); 
			String option0Str = productOption0E.select("label").text();
			if(option0Str!=null&&option0Str.equals("Size:")){
				ProductOption sizeOption = new ProductOption();
				Elements optionSizes = productOption0E.select("option");
				if (optionSizes != null && optionSizes.size() > 0) {
					List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

					sizeOption.setCustom_option_title("Size");
					sizeOption.setCustom_option_type("drop_down");
					sizeOption.setCustom_option_is_required("1");
					sizeOption.setCustom_option_sort_order("1");

					Iterator optionIterator = optionSizes.iterator();
					int i = 1;
					while (optionIterator.hasNext()) {
						Element option = (Element) optionIterator.next();
						String optionValue = option.text();
						if (optionValue.startsWith("Please")) {
							continue;
						} else {
							ProductOptionRow productOptionRow = new ProductOptionRow();
							if(optionValue.toLowerCase().contains("one")||optionValue.toLowerCase().contains("free")){
								productOptionRow.setCustom_option_row_title("Free Size");
								product.setIsFreeSize(1);
							}else{
								productOptionRow.setCustom_option_row_title(optionValue);
							}
							
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
			}else if(option0Str!=null&&option0Str.equals("Color:")){
				ProductOption colorOption = new ProductOption();
				Elements optionColors = productOption0E.select("option");
				if (optionColors != null && optionColors.size() > 0) {
					List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

					colorOption.setCustom_option_title("Color");
					colorOption.setCustom_option_type("drop_down");
					colorOption.setCustom_option_is_required("1");
					colorOption.setCustom_option_sort_order("1");

					Iterator optionColorIterator = optionColors.iterator();
					int i = 1;
					while (optionColorIterator.hasNext()) {
						
						Element option = (Element) optionColorIterator.next();
						String optionValue = option.text();						
											
						if (optionValue.startsWith("Please")) {
							continue;
						} else {					
							optionValue = DataDictionary.getVanclColor(optionValue.toLowerCase());
							if(optionValue!=null&&!optionValue.equals("")){
								ProductOptionRow productOptionRow = new ProductOptionRow();								
								productOptionRow.setCustom_option_row_title(optionValue);
								productOptionRow.setCustom_option_row_sort(i + "");
								productOptionRow.setProductOption(colorOption);
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								i++;
							}
						}
					}
					colorOption.setOption_rows(productOptionRowList);
				}
				colorOption.setProduct(product);
				productOptionList.add(colorOption);
				
			}
			
			Element productOption1E = doc.getElementById("select_same_goods_id_1"); 
			String option1Str = productOption1E.select("label").text();
			if(option1Str!=null&&option1Str.equals("Color:")){
				ProductOption colorOption = new ProductOption();
				Elements optionColors = productOption1E.select("option");
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
											
						if (optionValue.startsWith("Please")) {
							continue;
						} else {					
							optionValue = DataDictionary.getVanclColor(optionValue.toLowerCase());
							if(optionValue!=null&&!optionValue.equals("")){
								ProductOptionRow productOptionRow = new ProductOptionRow();								
								productOptionRow.setCustom_option_row_title(optionValue);
								productOptionRow.setCustom_option_row_sort(i + "");
								productOptionRow.setProductOption(colorOption);
								productOptionRowList.add(productOptionRow);
								if (i == 1) {
									product.setSb_color(optionValue);
								}
								i++;
							}
						}
					}
					colorOption.setOption_rows(productOptionRowList);
				}
				colorOption.setProduct(product);
				productOptionList.add(colorOption);
				
			}
			
			
			
			
			
			
			
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
			
			product.setProduct_options(productOptionList);
			
			//Description
			StringBuilder descriptionSb = new StringBuilder();
			
			Elements productDetails = doc.getElementsByClass("xxkkk");
			if(productDetails!=null&&productDetails.size()>0){
				String productDetail = productDetails.first().html();
				productDetail = productDetail.replaceAll("\r|\n", "").replaceAll(",", " ").replaceAll("One Size", "Free Size").replaceAll("one size", "Free Size").replaceAll("one-size", "Free Size");				
				
				descriptionSb.append("<div>").append(productDetail).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			Elements productInfos = doc.getElementsByClass("product_desc");
			if(productInfos!=null&&productInfos.size()>0){
				String productInfoStr = productInfos.first().html();
				String productInfo = productInfoStr.substring(0, productInfoStr.indexOf("</table>"))+"</table></div></div>";
				productInfo = productInfo.replaceAll("\r|\n", "").replaceAll(",", " ").replaceAll("<table border=\"0\"", "<table style=\"border-collapse:collapse;\" border=\"1\"").replaceAll("ffcccc", "666666").replaceAll("bgcolor=\"#cccccc\"", "").replaceAll("bgcolor=\"#999999\"", "");				
				
				descriptionSb.append("<div>").append(productInfo).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			Element productInfoSuojinE = doc.getElementById("suojin"); 
			if(productInfoSuojinE!=null){
				String productInfoSuojinStr = productInfoSuojinE.html();
				String productInfoSuojin = productInfoSuojinStr.substring(0, productInfoSuojinStr.indexOf("</table>"))+"</table></div>";
				productInfoSuojin = productInfoSuojin.replaceAll("\r|\n", "").replaceAll(",", " ");				
				
				descriptionSb.append("<div>").append(productInfoSuojin).append("</div>").append("<div style='clear:both;'></div>");
			}
			
			
			List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();

			Elements productMedias = doc.getElementsByClass("pro_intr_show_text");
			Elements productImages = productMedias.first().select("img");
			if (productImages != null) {
				descriptionSb.append("<div>");
				int j = 0;
				for (int i = 0; i < productImages.size(); i++) {
					ProductMedia productMedia = new ProductMedia();
					Element productImage = productImages.get(i);
					String imageUrl = productImage.attr("src");
					if("http://cloud3.faout.com/uploads/201207/heditor/201207241028595507.jpg".equals(imageUrl)){
						continue;
					}					
					if (j == 0) {
						product.setImage("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
						product.setSmall_image("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
						product.setThumbnail("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
					}
					productMedia.setMedia_image("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (j + 1)+ ".jpg");
					productMedia.setMedia_position((j + 1) + "");
					productMedia.setProduct(product);
					productMedia.setMedia_attribute_id("703");
					productMedia.setMedia_is_disabled("0");
					ProductMediaList.add(productMedia);
					
					//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
					//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
					super.downloadFile(imageUrl, SystemConfig.Image_Path()+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+product.getSku() + "-"+ (j + 1)+ ".jpg");
					descriptionSb.append("<img src='{{media url='upload/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (j + 1)+ ".jpg'}}' /><br/><br/>");
					j++;

				}
				descriptionSb.append("</div>");
			}

			if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
				descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/home/size.jpg'}}'/></div>");													
			}else if(categoryOption.equals("Shoes")){
				descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/home/shoes-size.jpg'}}'/></div>");													
			}
			product.setDescription(descriptionSb.toString());
			product.setProduct_medias(ProductMediaList);
			

		} catch (Exception e) {
			System.out.println("------parse Product error:--"+e.getMessage());
			e.printStackTrace();
			product = null;
			return product;
			
		}		
		return product;
	}
}
