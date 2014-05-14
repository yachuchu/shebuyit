package com.shebuyit.crawler.parser;

import java.io.IOException;
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
import com.shebuyit.crawler.util.ToolUtils;
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

public class SheinsideCrawler extends Crawler{
	
	private IProductService productService;
	
	private IShopService shopService;
	
	private IConfigService configService;
		
	private IDictService dictService;
	
	private static SheinsideCrawler sheinside;
	
	
	
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

		sheinside = this;

		sheinside.productService = this.productService;
		
		sheinside.configService = this.configService;
		
		sheinside.shopService = this.shopService;
		
		sheinside.dictService = this.dictService;

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
		String tempPageURL = indexURL.replaceAll(".html", "-p%p.html");
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
			
			Element pageContent = doc.getElementById("box-pagelist");
			if (pageContent != null) {
				String pageString = pageContent.select("span").first().text();
				String pageNumber = pageString.substring(pageString.indexOf("of") + 2);
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
			Thread.sleep(50000);
//			org.jsoup.nodes.Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:5.0)").get();
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			Elements productsContents = doc.getElementsByClass("products_category");
			Elements listProducts = productsContents.last().getElementsByClass("box-product-list");
			for (Element ele : listProducts) {
				String productUrl = ele.select("a").attr("href");		
				Product product = parseProductInfoUrl(productUrl);
				if(product!=null){
//					/productList.add(product);
					sheinside.productService.save(product);
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
			Thread.sleep(20000);
//			org.jsoup.nodes.Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:5.0)").get();
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			product = new Product();
			Element productSKUE = doc.getElementById("productCodeSpan");
			String productSku = productSKUE.text();
			product.setProductSku(productSku);
			
			//判断product是否已经存在
			Product existProduct = new Product();
			existProduct.setProductSku(productSku);
			List<Product> existProducts = sheinside.productService.queryProduct(existProduct);
			if(existProducts!=null&&existProducts.size()>0){
				
				System.out.println("product---------"+product.getProductSku()+"----------------------exist-----------------------------------");						
				product = null;
				return product;
			}
			
			//获得sku序号,设置sku
			Config config = new Config();
			config.setConfigName("sheinsideSku");
			List<Config> configs = sheinside.configService.queryConfig(config);
			if(configs!=null&&configs.size()>0){
				Config con = configs.get(0);
				Long skuNumber = Long.valueOf(con.getConfigValue())+1;
				System.out.println("product---sheinsideSku------"+skuNumber);						
				
				product.setSku("SKU"+skuNumber.toString());
				
				//保存新的sku序号
				con.setConfigValue(skuNumber.toString());
				sheinside.configService.update(con);				
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
			
			Elements listProducts = doc.getElementsByClass("good_descright");
			String productName = listProducts.first().select("h1").text();
			product.setEnglishName(productName);
			
			//数据字典
			String[] nameArray = productName.split(" ");
			if(nameArray!=null){
				for(int i = 0;i<nameArray.length;i++){
					String nameDict = nameArray[i];
					Dict dict = new Dict();
					dict.setName(nameDict);
					List<Dict> dicts = sheinside.dictService.queryDict(dict);
					if(dicts==null||dicts.size()==0){			
						//保存数据字典
						dict.setCode(nameDict);
						dict.setCreated_time(format.format(new Date()));
						sheinside.dictService.save(dict);				
					}else{
						continue;
					}
				}
			}
			
			
			
			Element productSpecialPriceE = doc.getElementById("special_price_u");
			if(productSpecialPriceE!=null){
			String productSpecialPriceStr = productSpecialPriceE.attr("price");
				if(productSpecialPriceStr!=null&&!productSpecialPriceStr.equals("")){
					double productSpecialPriceD = Double.parseDouble(productSpecialPriceStr.substring(3));
					product.setPrice(String.format("%.2f",productSpecialPriceD*1.5));
					double price = Double.parseDouble(productSpecialPriceStr.substring(3))-1;
					product.setSpecial_price(String.format("%.2f",price));
				}
			}else{
				Element productShopPriceE = doc.getElementById("shop_price_u");
				String productShopPriceStr = productShopPriceE.attr("price")==null?"100":productShopPriceE.attr("price").substring(3);	
				double productShopPriceD = Double.parseDouble(productShopPriceStr);
				if(productShopPriceD!=100){
					product.setPrice(String.format("%.2f",productShopPriceD*1.5));
				}else{
					product.setPrice(productShopPriceStr);
				}
				
				double price = Double.parseDouble(productShopPriceStr)-1;			
				product.setSpecial_price(String.format("%.2f",price));
			}			
			
			if(product.getPrice()==null||product.getPrice().equals("")){
				product = null;
				return product;
			}

			//System.out.println("productPrice--------------" + product.getPrice());

			List<ProductOption> productOptionList = new ArrayList<ProductOption>();
			ProductOption productOption = new ProductOption();
			Elements optionSizes = doc.select("option");
			if (optionSizes != null && optionSizes.size() > 0) {
				List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

				productOption.setCustom_option_title("Size");
				productOption.setCustom_option_type("drop_down");
				productOption.setCustom_option_is_required("1");
				productOption.setCustom_option_sort_order("1");

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
						productOptionRow.setProductOption(productOption);
						productOptionRowList.add(productOptionRow);
						i++;
					}
				}
				productOption.setOption_rows(productOptionRowList);
			}
			productOption.setProduct(product);
			productOptionList.add(productOption);
			
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
			
			product.setProduct_options(productOptionList);

			StringBuilder descriptionSb = new StringBuilder();
			Elements productDescriptions = doc
					.getElementsByClass("ItemSpecificationCenter");

			String productDescription = productDescriptions.first()
					.select("ul").toString();
			productDescription = productDescription.replaceAll("\r|\n", "")
					.replaceAll(",", " ").replaceAll("One Size", "Free Size").replaceAll("one size", "Free Size").replaceAll("one-size", "Free Size");

			// 
			Elements descriptionLis = productDescriptions.first().select("li");
			for (Element descriptionLi : descriptionLis) {
				String descriptionLiKey = descriptionLi.select("span").first().text();
				if (descriptionLiKey.toLowerCase().equals("color :")) {
					String color = descriptionLi.select("i").first().text();
					product.setSb_color(color);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("material :")) {
					String material = descriptionLi.select("i").first().text();
					product.setSb_material(material);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("style :")) {
					String style = descriptionLi.select("i").first().text();
					product.setSb_style(style);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("types :")||descriptionLiKey.toLowerCase().equals("type :")) {
					String types = descriptionLi.select("i").first().text();
					product.setSb_types(types);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("season :")) {
					String season = descriptionLi.select("i").first().text();
					product.setSb_season(season);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("item :")) {
					String item = descriptionLi.select("i").first().text();
					product.setSb_item(item);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("neckline :")) {
					String neckline = descriptionLi.select("i").first().text();
					product.setSb_collar(neckline);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("sleeve length :")) {
					String sleeve_length = descriptionLi.select("i").first().text();
					if(!sleeve_length.toLowerCase().contains("cm")){
						product.setSb_sleeve_length(sleeve_length);
					}
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("decoration :")) {
					String decoration = descriptionLi.select("i").first().text();
					product.setSb_decoration(decoration);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("pattern type :")) {
					String pattern_type = descriptionLi.select("i").first().text();
					product.setSb_pattern_type(pattern_type);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("length :")) {
					String length = descriptionLi.select("i").first().text();
					if(!length.toLowerCase().contains("cm")||!ToolUtils.hasNumeric(length.toLowerCase())){
						product.setSb_length(length);
					}
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("collar :")) {
					String collar = descriptionLi.select("i").first().text();
					product.setSb_collar(collar);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("placket :")) {
					String placket = descriptionLi.select("i").first().text();
					product.setSb_placket(placket);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("silhouette :")) {
					String silhouette = descriptionLi.select("i").first().text();
					product.setSb_silhouette(silhouette);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("dresses length :")) {
					String dresses_length = descriptionLi.select("i").first().text();
					product.setSb_dresses_length(dresses_length);
					continue;
				}			

			}

			List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();

			descriptionSb.append("<div>").append(productDescription).append("</div>").append("<div style='clear:both;'></div>");
			Elements productImages = doc.getElementsByClass("otheImg_li");
			if (productImages != null) {
				descriptionSb.append("<div>");
				for (int i = 0; i < productImages.size(); i++) {
					ProductMedia productMedia = new ProductMedia();
					Element productImage = productImages.get(i);
					String imageUrl = productImage.select("img").first().attr("bigimg");
					if (i == 0) {
						product.setImage("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
						product.setSmall_image("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
						product.setThumbnail("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
					}
					productMedia.setMedia_image("/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
					productMedia.setMedia_position((i + 1) + "");
					productMedia.setProduct(product);
					productMedia.setMedia_attribute_id("703");
					productMedia.setMedia_is_disabled("0");
					ProductMediaList.add(productMedia);

					//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
					//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
					super.downloadFile(imageUrl, SystemConfig.Image_Path()+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
					descriptionSb.append("<img src='{{media url='upload/"+this.shop.getShopSku()+"/"+this.shop.getCategory()+"/"+ product.getSku() + "-" + (i + 1)+ ".jpg'}}' /><br/><br/>");

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
			List<Product> existProducts = sheinside.productService.queryProduct(existProduct);
			if(existProducts!=null&&existProducts.size()>0){
				
				System.out.println("product---------"+product.getProductSku()+"----------------------exist-----------------------------------");						
				product = null;
				return product;
			}
			
			//获得sku序号,设置sku
			Config config = new Config();
			config.setConfigName("sheinsideSku");
			List<Config> configs = sheinside.configService.queryConfig(config);
			if(configs!=null&&configs.size()>0){
				Config con = configs.get(0);
				Long skuNumber = Long.valueOf(con.getConfigValue())+1;
				System.out.println("product---sheinsideSku------"+skuNumber);						
				
				product.setSku("SKU"+skuNumber.toString());
				
				//保存新的sku序号
				con.setConfigValue(skuNumber.toString());
				sheinside.configService.update(con);				
			}else{
				product = null;
				return product;
			}
						
			
			Long stock =  Math.round(Math.random()*(200-50)+50);
			product.setQty(stock.toString());
			product.setStock(1);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setSb_shop(pageUrl);
			//product.setSb_brand(shopBrand);
			String weight = DataDictionary.getWeight(product.getCategory().replaceAll(" ", "_"));
			product.setWeight(weight);
			
			Elements listProducts = doc.getElementsByClass("good_descright");
			String productName = listProducts.first().select("h1").text();
			product.setEnglishName(productName);
			
			//数据字典
			String[] nameArray = productName.split(" ");
			if(nameArray!=null){
				for(int i = 0;i<nameArray.length;i++){
					String nameDict = nameArray[i];
					Dict dict = new Dict();
					dict.setName(nameDict);
					List<Dict> dicts = sheinside.dictService.queryDict(dict);
					if(dicts==null||dicts.size()==0){			
						//保存数据字典
						dict.setCode(nameDict);
						dict.setCreated_time(format.format(new Date()));
						sheinside.dictService.save(dict);				
					}else{
						continue;
					}
				}
			}
			
			
			
			Element productSpecialPriceE = doc.getElementById("special_price_u");
			if(productSpecialPriceE!=null){
			String productSpecialPriceStr = productSpecialPriceE.attr("price");
				if(productSpecialPriceStr!=null&&!productSpecialPriceStr.equals("")){
					double productSpecialPriceD = Double.parseDouble(productSpecialPriceStr.substring(1));
					product.setPrice(String.format("%.2f",productSpecialPriceD*1.5));
					double price = Double.parseDouble(productSpecialPriceStr.substring(1))-1;
					product.setSpecial_price(String.format("%.2f",price));
				}
			}else{
				Element productShopPriceE = doc.getElementById("shop_price_u");
				String productShopPriceStr = productShopPriceE.attr("price")==null?"100":productShopPriceE.attr("price").substring(3);	
				double productShopPriceD = Double.parseDouble(productShopPriceStr);
				if(productShopPriceD!=100){
					product.setPrice(String.format("%.2f",productShopPriceD*1.5));
				}else{
					product.setPrice(productShopPriceStr);
				}
				
				double price = Double.parseDouble(productShopPriceStr)-1;			
				product.setSpecial_price(String.format("%.2f",price));
			}			
			
			if(product.getPrice()==null||product.getPrice().equals("")){
				product = null;
				return product;
			}

			//System.out.println("productPrice--------------" + product.getPrice());

			List<ProductOption> productOptionList = new ArrayList<ProductOption>();
			ProductOption productOption = new ProductOption();
			Elements optionSizes = doc.select("option");
			if (optionSizes != null && optionSizes.size() > 0) {
				List<ProductOptionRow> productOptionRowList = new ArrayList<ProductOptionRow>();

				productOption.setCustom_option_title("Size");
				productOption.setCustom_option_type("drop_down");
				productOption.setCustom_option_is_required("1");
				productOption.setCustom_option_sort_order("1");

				Iterator optionIterator = optionSizes.iterator();
				int i = 1;
				while (optionIterator.hasNext()) {
					Element option = (Element) optionIterator.next();
					String optionValue = option.text();
					if (optionValue.startsWith("Please")) {
						continue;
					} else {
						ProductOptionRow productOptionRow = new ProductOptionRow();
						if(optionValue.toLowerCase().contains("one")){
							productOptionRow.setCustom_option_row_title("Free Size");
						}else{
							productOptionRow.setCustom_option_row_title(optionValue);
						}
						
						productOptionRow.setCustom_option_row_sort(i + "");
						productOptionRow.setProductOption(productOption);
						productOptionRowList.add(productOptionRow);
						i++;
					}
				}
				productOption.setOption_rows(productOptionRowList);
			}
			productOption.setProduct(product);
			productOptionList.add(productOption);
			
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
			
			product.setProduct_options(productOptionList);

			StringBuilder descriptionSb = new StringBuilder();
			Elements productDescriptions = doc
					.getElementsByClass("ItemSpecificationCenter");

			String productDescription = productDescriptions.first()
					.select("ul").toString();
			productDescription = productDescription.replaceAll("\r|\n", "")
					.replaceAll(",", " ");

			// 
			Elements descriptionLis = productDescriptions.first().select("li");
			for (Element descriptionLi : descriptionLis) {
				String descriptionLiKey = descriptionLi.select("span").first().text();
				if (descriptionLiKey.toLowerCase().equals("color :")) {
					String color = descriptionLi.select("i").first().text();
					product.setSb_color(color);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("material :")) {
					String material = descriptionLi.select("i").first().text();
					product.setSb_material(material);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("style :")) {
					String style = descriptionLi.select("i").first().text();
					product.setSb_style(style);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("types :")||descriptionLiKey.toLowerCase().equals("type :")) {
					String types = descriptionLi.select("i").first().text();
					product.setSb_types(types);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("season :")) {
					String season = descriptionLi.select("i").first().text();
					product.setSb_season(season);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("item :")) {
					String item = descriptionLi.select("i").first().text();
					product.setSb_item(item);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("neckline :")) {
					String neckline = descriptionLi.select("i").first().text();
					product.setSb_collar(neckline);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("sleeve length :")) {
					String sleeve_length = descriptionLi.select("i").first().text();
					if(!sleeve_length.toLowerCase().contains("cm")){
						product.setSb_sleeve_length(sleeve_length);
					}
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("decoration :")) {
					String decoration = descriptionLi.select("i").first().text();
					product.setSb_decoration(decoration);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("pattern type :")) {
					String pattern_type = descriptionLi.select("i").first().text();
					product.setSb_pattern_type(pattern_type);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("length :")) {
					String length = descriptionLi.select("i").first().text();
					if(!length.toLowerCase().contains("cm")||!ToolUtils.hasNumeric(length.toLowerCase())){
						product.setSb_length(length);
					}
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("collar :")) {
					String collar = descriptionLi.select("i").first().text();
					product.setSb_collar(collar);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("placket :")) {
					String placket = descriptionLi.select("i").first().text();
					product.setSb_placket(placket);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("silhouette :")) {
					String silhouette = descriptionLi.select("i").first().text();
					product.setSb_silhouette(silhouette);
					continue;
				}else if (descriptionLiKey.toLowerCase().equals("dresses length :")) {
					String dresses_length = descriptionLi.select("i").first().text();
					product.setSb_dresses_length(dresses_length);
					continue;
				}			

			}

			List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();

			descriptionSb.append("<div>").append(productDescription).append("</div>").append("<div style='clear:both;'></div>");
			Elements productImages = doc.getElementsByClass("otheImg_li");
			if (productImages != null) {
				descriptionSb.append("<div>");
				for (int i = 0; i < productImages.size(); i++) {
					ProductMedia productMedia = new ProductMedia();
					Element productImage = productImages.get(i);
					String imageUrl = productImage.select("img").first().attr("bigimg");
					if (i == 0) {
						product.setImage("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
						product.setSmall_image("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
						product.setThumbnail("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
					}
					productMedia.setMedia_image("/manual/"+product.getShopSku()+"/"+product.getCategory()+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
					productMedia.setMedia_position((i + 1) + "");
					productMedia.setProduct(product);
					productMedia.setMedia_attribute_id("703");
					productMedia.setMedia_is_disabled("0");
					ProductMediaList.add(productMedia);

					//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
					//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
					super.downloadFile(imageUrl, SystemConfig.Image_Path()+"/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
					descriptionSb.append("<img src='{{media url='upload/manual/"+product.getShopSku()+"/"+product.getCategory()+"/"+ product.getSku() + "-" + (i + 1)+ ".jpg'}}' /><br/><br/>");

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
