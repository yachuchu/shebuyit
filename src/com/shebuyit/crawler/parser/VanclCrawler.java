package com.shebuyit.crawler.parser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
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

public class VanclCrawler extends Crawler {
	
private IProductService productService;
	
	private IShopService shopService;
	
	private IConfigService configService;
		
	private IDictService dictService;
	
	private static VanclCrawler vancl;
	
	
	
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

		vancl = this;

		vancl.productService = this.productService;
		
		vancl.configService = this.configService;
		
		vancl.shopService = this.shopService;
		
		vancl.dictService = this.dictService;

    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		doStart(shop);
	}
	
	private static int productNumber = 1;
	
	public void doStart(Shop shop) {
		String indexURL = shop.getShopUrl();
		System.out.println("------Parser---Start---------");
		String tempPageURL  = indexURL.replaceAll("-1--", "-%p--");
		JSONArray array = new JSONArray();
		int pageCount = getPageCount(indexURL);
		for (int i = 1; i < pageCount+1; i++) {
			String pageUrl = tempPageURL.replaceAll("%p", i + "");
			start(pageUrl, array);			
		}	
		System.out.println("------Parser---End---------");

	}
	
	public int getPageCount(String url) {
		int pageCount = 1;
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			
			Elements listPages = doc.getElementsByClass("listPage");
			if (listPages != null) {
				Elements pageALinks = listPages.first().select("a");
				if (pageALinks != null&&!pageALinks.isEmpty()) {
					int size = pageALinks.size();
					String pageNumber = pageALinks.get(size-2).text();
					pageCount = Integer.parseInt(pageNumber.trim());
					System.out.println("---total------pageCount--------" + pageCount);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageCount;
	}
	
	
	public void start(String url, JSONArray array) {
		try {
			String content = super.doGet(url);
			Document doc = Jsoup.parse(content);
			Elements listProducts = doc.getElementsByClass("pic");
			JSONArray tempJsonArray = new JSONArray();
			for (Element ele : listProducts) {
				if(ele.tagName().equals("div")){
					String productId = ele.getElementsByClass("prouctCode").first().text();
					String productUrl = ele.select("a").first().attr("href");		
					Product product = parseProductInfoUrl(productId, productUrl);	
					if(product!=null){
//						/productList.add(product);
						vancl.productService.save(product);
						System.out.println(productNumber+"---------productNumber--------------------------------------------------------------");
						productNumber++;	
					}	
				}
							
				
			}
			array.put(tempJsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	//解析product 信息
	public Product parseProductInfoUrl(String productId, String pageUrl) {
		System.out.println("productUrl--------------" + pageUrl);
		Product product = null;
		try {
			String content = super.doGet(pageUrl);
			Document doc = Jsoup.parse(content);
			
			String productHtml = doc.html().toString();
			if(productHtml.indexOf("Out of stock.")==-1){
				
				product = new Product();
				Element productInfoElement = doc.getElementById("Fresh2");
				String productSkuStr = productInfoElement.select("p").first().text();
				String productSku = productSkuStr.substring(productSkuStr.indexOf(":")+1);
				product.setProductSku(productSku);				
				//判断product是否已经存在
				Product existProduct = new Product();
				existProduct.setProductSku(productSku);
				List<Product> existProducts = vancl.productService.queryProduct(existProduct);
				if(existProducts!=null&&existProducts.size()>0){
					
					System.out.println("product---------"+product.getProductSku()+"----------------------exist-----------------------------------");						
					product = null;
					return product;
				}
				
				//获得sku序号,设置sku
				Config config = new Config();
				config.setConfigName("skuNumber");
				List<Config> configs = vancl.configService.queryConfig(config);
				if(configs!=null&&configs.size()>0){
					Config con = configs.get(0);
					Long skuNumber = Long.valueOf(con.getConfigValue())+1;
					System.out.println("product---skuNumber------"+skuNumber);						
					
					product.setSku("SKU"+skuNumber.toString());
					
					//保存新的sku序号
					con.setConfigValue(skuNumber.toString());
					vancl.configService.update(con);				
				}else{
					product = null;
					return product;
				}
				
				Long stock =  Math.round(Math.random()*(200-50)+50);
				product.setQty(stock.toString());
				product.setStock(1);
				
				String productName = productInfoElement.select("span").first().text();
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				product.setCreated_time(new Date());
				product.setUpdated_at(format.format(new Date()));
				
				String category = this.shop.getCategory();
				String attribute_set = this.shop.getAttribute_set();
				
				if(productName.toLowerCase().contains("skirt")){
					category = "Bottoms/Skirts";
					attribute_set = "Skirts_set";
				}
				product.setCategory(category);
				product.setAttribute_set(attribute_set);
				product.setSb_shop(pageUrl);
				product.setShopSku(this.shop.getShopSku());
				
				product.setMedia_gallery("Media_gallery");
				String weight = DataDictionary.getWeight(category.replaceAll(" ", "_"));
				product.setWeight(weight);

							
				
				
				product.setEnglishName(productName);
				//数据字典
				String[] nameArray = productName.split(" ");
				if(nameArray!=null){
					for(int i = 0;i<nameArray.length;i++){
						String nameDict = nameArray[i];
						Dict dict = new Dict();
						dict.setName(nameDict);
						List<Dict> dicts = vancl.dictService.queryDict(dict);
						if(dicts==null||dicts.size()==0){			
							//保存数据字典
							dict.setCode(nameDict);
							dict.setCreated_time(format.format(new Date()));
							vancl.dictService.save(dict);				
						}else{
							continue;
						}
					}
				}
				
				
				double expressFee = DataDictionary.getExpressFee(category.replaceAll(" ", "_"));	
				String productShopPriceStr = productInfoElement.select("nobr").first().text();
				String productShopPrice = productShopPriceStr.substring(productShopPriceStr.indexOf(":")+3);
				double price = Double.parseDouble(productShopPrice)*1.3;
				product.setPrice(String.format("%.2f",price*1.5));	
				product.setSpecial_price(String.format("%.2f",price));
				

				System.out.println("productPrice--------------" + product.getPrice());
				
				Element productColorElement = doc.getElementById("selectedColorName");
				String color = productColorElement.text();
				product.setSb_color(DataDictionary.getVanclColor(color));
				

				List<ProductOption> productOptionList = new ArrayList<ProductOption>();
				ProductOption sizeOption = new ProductOption();
				Elements optionSizes = doc.getElementsByClass("selSizeLi");
				if (optionSizes != null && optionSizes.size() > 0) {
					product.setHas_options("1");
					product.setRequired_options("1");
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
						ProductOptionRow productOptionRow = new ProductOptionRow();
						productOptionRow.setCustom_option_row_title(optionValue);
						productOptionRow.setCustom_option_row_sort(i + "");
						productOptionRow.setProductOption(sizeOption);
						productOptionRowList.add(productOptionRow);
						i++;
					}
					sizeOption.setOption_rows(productOptionRowList);
				}
				sizeOption.setProduct(product);
				productOptionList.add(sizeOption);
				product.setProduct_options(productOptionList);

				StringBuilder descriptionSb = new StringBuilder();
				
				Element productDescriptionE = doc.getElementById("StyleAttributes");
				if(productDescriptionE!=null){
				String productDescription = productDescriptionE.select("div").get(1).html();
				productDescription = productDescription.replaceAll("\r|\n", "").replaceAll(",", " ").replaceAll("&nbsp;&nbsp;", " ").replaceAll("VANCL", "SHEDRESSUP");
				descriptionSb.append("<div>").append(productDescription).append("</div>").append("<div style='clear:both;'></div>");				
				}
				
				Element productSizetableE = doc.getElementById("sizetable");
				if(productSizetableE!=null){
					String productSizetable = productSizetableE.select("table").toString();
					productSizetable = productSizetable.replaceAll("\r|\n", "").replaceAll(",", " ").replaceAll("&nbsp;&nbsp;", " ").replaceAll("（", "(").replaceAll("）", ")").replaceAll("<table", "<table border='1' cellspacing='0' cellpadding='0'");
					descriptionSb.append("<br></br><div>").append(productSizetable).append("</div>").append("<div style='clear:both;'></div><br></br>");
				}
				
				
				
				List<ProductMedia> ProductMediaList = new ArrayList<ProductMedia>();			
				Element productImageE = doc.getElementById("mycarousel");			
				Elements productImages = productImageE.select("img");
				if (productImages != null) {
					descriptionSb.append("<div>");
					for (int i = 0; i < productImages.size(); i++) {
						ProductMedia productMedia = new ProductMedia();
						Element productImage = productImages.get(i);
						String imageUrl = productImage.absUrl("src").replaceAll("small", "Big");

						if (i == 0) {
							product.setImage("/"+this.shop.getShopSku()+"/"+category+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
							product.setSmall_image("/"+this.shop.getShopSku()+"/"+category+"/" + product.getSku() + "-" + (i + 1)+ ".jpg");
							product.setThumbnail("/"+this.shop.getShopSku()+"/"+category+"/" + product.getSku() + "-" + (i + 1)
									+ ".jpg");
						}
						productMedia.setMedia_image("/"+this.shop.getShopSku()+"/"+category+"/" + product.getSku() + "-" + (i + 1)
								+ ".jpg");
						productMedia.setMedia_position((i + 1) + "");
						productMedia.setProduct(product);
						productMedia.setMedia_attribute_id("703");
						productMedia.setMedia_is_disabled("0");
						ProductMediaList.add(productMedia);

						//HttpDownloadTool.download(imageUrl, 3,  productId + "-"+ (i + 1)+ ".jpg",this.category);
						//ImageDownloadTool.downLoadImage(imageUrl,  productId + "-"+ (i + 1)+ ".jpg",this.category);				
						super.downloadFile(imageUrl, SystemConfig.Image_Path()+this.shop.getShopSku()+"/"+category+"/"+product.getSku() + "-"+ (i + 1)+ ".jpg");
						descriptionSb.append("<img width='400' height='400' src='{{media url='upload/"+this.shop.getShopSku()+"/"+category+"/"+ product.getSku() + "-" + (i + 1)+ ".jpg'}}' /><br/><br/>");

					}
					descriptionSb.append("</div>");
				}
				
				String categoryOption = DataDictionary.getCategoryOption(category);
				if(categoryOption.equals("Tops")||categoryOption.equals("Bottoms")){
					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/home/size.jpg'}}'/></div>");													
				}else if(categoryOption.equals("Shoes")){
					descriptionSb.append("<div style='clear:both;'></div><div><a name='size'></a><img src='{{media url='upload/home/shoes-size.jpg'}}'/></div>");													
				}

				product.setDescription(descriptionSb.toString());
				product.setProduct_medias(ProductMediaList);
				
				}
			} catch (Exception e) {
				e.printStackTrace();
			}				
		return product;			
	}

}
