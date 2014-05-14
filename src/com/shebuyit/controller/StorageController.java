package com.shebuyit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.ibm.json.java.JSONArray;
import com.shebuyit.crawler.file.CSVFile;
import com.shebuyit.crawler.util.DataUtils;
import com.shebuyit.crawler.util.SystemConfig;
import com.shebuyit.po.Product;
import com.shebuyit.po.ProductOption;
import com.shebuyit.po.ProductOptionRow;
import com.shebuyit.po.Shop;
import com.shebuyit.service.IProductService;
import com.shebuyit.service.IShopService;
import com.shebuyit.vo.DojoGridVO;

public class StorageController extends BaseMultiActionController{	
	private IProductService productService;
	
	private IShopService shopService;
	
	private String storageSearchView;
	
	private String storageView;
		
	public static String SEARCH_PARAM = "search_param";
	
	public static String SEARCH_PARAM_SKU = "sku";
	
	public static String SEARCH_PARAM_ORLSKU = "orl_sku";
	
	public static String SEARCH_PARAM_NAME = "name";
	
	public static String SEARCH_PARAM_DESCRIPTION = "description";
	
	public static String SEARCH_PARAM_SHOPSKU = "shopSku";
	
	public static String SEARCH_PARAM_CATEGORY = "category";
	
	public static String SEARCH_PARAM_VALUE = "param_value";
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	public static String SEARCH_PARAM_FREESIZE = "freeSize";
	
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

	
	public String getStorageSearchView() {
		return storageSearchView;
	}

	public void setStorageSearchView(String storageSearchView) {
		this.storageSearchView = storageSearchView;
	}

	public String getStorageView() {
		return storageView;
	}

	public void setStorageView(String storageView) {
		this.storageView = storageView;
	}

	/**
	 * The batch list page, list existing batches. it needs to be binded with active collection.
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */			
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {		
		Map categoryMap = DataUtils.categoryMap();
		List<Shop> shops = shopService.queryShop(new Shop());
		Map<String,String> shopSkuMap = new HashMap<String,String>();
		for(Shop shop:shops){
			if(!shopSkuMap.containsKey(shop.getShopSku())){
				shopSkuMap.put(shop.getShopSku(),shop.getShopBrand());
			}
		}
				
		String doSearch = request.getParameter("doSearch");
		String currentPageNumStr = request.getParameter("paging.current");
		if (currentPageNumStr == null) {
			currentPageNumStr = "1";
		}
		int currentPageNum = 0;
		currentPageNum = Integer.valueOf(currentPageNumStr);
		if (dojoGridVO.getPaging().getSize() == 0) {
			dojoGridVO.getPaging().setSize(15);
			dojoGridVO.getPaging().setCurrent(currentPageNum);
		}
		
		String searchParam = request.getParameter(StorageController.SEARCH_PARAM);
		String searchParamShopsku = request.getParameter(StorageController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(StorageController.SEARCH_PARAM_CATEGORY);
		String searchParamFreeSize = request.getParameter(StorageController.SEARCH_PARAM_FREESIZE);
		String paramStart = request.getParameter(StorageController.SEARCH_PARAM_START);		
		String paramEnd = request.getParameter(StorageController.SEARCH_PARAM_END);
		Product product = new Product();
		product.setStock(0);
		String defaultSortBy = "created_time";
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(StorageController.SEARCH_PARAM_SKU)) {
				product.setSku(request.getParameter(StorageController.SEARCH_PARAM_VALUE));				
				queryCondition.append("&"+StorageController.SEARCH_PARAM+"="+StorageController.SEARCH_PARAM_SKU+"&"+StorageController.SEARCH_PARAM_SKU+"=" + request.getParameter(StorageController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(StorageController.SEARCH_PARAM_NAME)) {
				product.setEnglishName(request.getParameter(StorageController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+StorageController.SEARCH_PARAM+"="+StorageController.SEARCH_PARAM_NAME+"&"+StorageController.SEARCH_PARAM_NAME+"=" + request.getParameter(StorageController.SEARCH_PARAM_VALUE));
			}
		}
		if (searchParamShopsku != null){
				product.setShopSku(request.getParameter(StorageController.SEARCH_PARAM_SHOPSKU));				
				queryCondition.append("&"+StorageController.SEARCH_PARAM_SHOPSKU+"=" + request.getParameter(StorageController.SEARCH_PARAM_SHOPSKU));
			
		}
		if (searchParamCategory != null){
			product.setCategory(request.getParameter(StorageController.SEARCH_PARAM_CATEGORY));				
			queryCondition.append("&"+StorageController.SEARCH_PARAM_CATEGORY+"=" + request.getParameter(StorageController.SEARCH_PARAM_CATEGORY));
		
		}
		if (searchParamFreeSize != null&&!searchParamFreeSize.equals("")){
			product.setIsFreeSize(Integer.parseInt(request.getParameter(StorageController.SEARCH_PARAM_FREESIZE)));				
			queryCondition.append("&"+StorageController.SEARCH_PARAM_FREESIZE+"=" + request.getParameter(StorageController.SEARCH_PARAM_FREESIZE));
		
		}
		if (paramStart != null && !"".equals(paramStart)){
			product.setTime_start(paramStart);
			queryCondition.append("&"+StorageController.SEARCH_PARAM_START+"=" + request.getParameter(StorageController.SEARCH_PARAM_START));			
		}
		if (paramEnd != null && !"".equals(paramEnd)){
			product.setTime_end(paramEnd);
			queryCondition.append("&"+StorageController.SEARCH_PARAM_END+"=" + request.getParameter(StorageController.SEARCH_PARAM_END));			
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(storageSearchView);
		try {
			String sortby = request.getParameter("sortby");
			String order = request.getParameter("order");
			boolean isAsc = false;
			if (!(sortby != null && sortby.trim().length() != 0)) {
				if(defaultSortBy == null)
					sortby = "id";
				else
					sortby = defaultSortBy;
			}
			if (order != null && order.equals("ASC")) {
				isAsc = true;
			}
			List<Product> products = productService.queryProductByQueryCondition(product, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(products);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),products, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
			mv.addObject("shopSkuMap", shopSkuMap);
			mv.addObject("categoryMap", categoryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	

	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(storageView);
		String productId = request.getParameter("productId");
		String returnCode = request.getParameter("returnCode");
		Product product = productService.get(productId);
		List<ProductOption> product_options = product.getProduct_options();
		List<ProductOptionRow> colorSet = new ArrayList<ProductOptionRow>();
		for (ProductOption option : product_options) {	
			if(option.getCustom_option_title().equals("Color")){
				colorSet = option.getOption_rows();
				Collections.sort(colorSet, new Comparator(){ 
				     @Override  
				    public int compare(Object o1, Object o2) {  
				    	 ProductOptionRow iso1 = (ProductOptionRow)o1;  
				    	 ProductOptionRow iso2 = (ProductOptionRow)o2;  
				        String str1 = iso1.getCustom_option_row_sort();  
				        String str2 = iso2.getCustom_option_row_sort();  
				        return str1.compareTo(str2);  
				    } 
				}); 
			}
		}
		
		Map colorMap = DataUtils.colorMap();
		Map topsTypesMap = DataUtils.topsTypesMap();
		Map bottomsTypesMap = DataUtils.bottomsTypesMap();
		Map styleMap = DataUtils.styleMap();
		Map itemMap = DataUtils.itemMap();
		Map sleeveLengthMap = DataUtils.sleeveLengthMap();
		Map collarMap = DataUtils.collarMap();
		Map dressesLengthMap = DataUtils.dressesLengthMap();
		Map waistTypeMap = DataUtils.waistTypeMap();
//		Map lengthMap = DataUtils.lengthMap();
		Map patternMap = DataUtils.patternMap();
		Map materialMap = DataUtils.materialMap();
		Map seasonMap = DataUtils.seasonMap();
		Map categoryMap = DataUtils.categoryMap();
		Map attributeSetMap = DataUtils.attributeSetMap();
		Map decorationMap = DataUtils.decorationMap();
		Map genderMap = DataUtils.genderMap();
		Map sleeveTypeMap = DataUtils.sleeveTypeMap();
		Map componentContentMap = DataUtils.componentContentMap();
		Map closureTypeMap = DataUtils.closureTypeMap();
		
		
		
		mv.addObject("product", product);
		mv.addObject("returnCode", returnCode);
		mv.addObject("colorMap", colorMap);
		mv.addObject("topsTypesMap", topsTypesMap);
		mv.addObject("bottomsTypesMap", bottomsTypesMap);
		mv.addObject("styleMap", styleMap);
		mv.addObject("itemMap", itemMap);
		mv.addObject("sleeveLengthMap", sleeveLengthMap);
		mv.addObject("collarMap", collarMap);
		mv.addObject("dressesLengthMap", dressesLengthMap);
		mv.addObject("waistTypeMap", waistTypeMap);
		mv.addObject("patternMap", patternMap);
		mv.addObject("materialMap", materialMap);
		mv.addObject("seasonMap", seasonMap);
		mv.addObject("categoryMap", categoryMap);
		mv.addObject("attributeSetMap", attributeSetMap);
		mv.addObject("decorationMap", decorationMap);
		
		mv.addObject("genderMap", genderMap);
		mv.addObject("sleeveTypeMap", sleeveTypeMap);
		mv.addObject("componentContentMap", componentContentMap);
		mv.addObject("closureTypeMap", closureTypeMap);
		
		mv.addObject("colorSet", colorSet);
		
		return mv;
	}
	
	
	/**
	 * It is a dummy code, need to be replaced.
	 * The create method will be called in ajax way.
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws ServletException, IOException {				
		String productId = request.getParameter("productId");
		String productSku = request.getParameter("productSku");
		String productUrl = request.getParameter("productUrl");
		String stock = request.getParameter("stock");		
		String englishName = request.getParameter("englishName");
		String chineseName = request.getParameter("chineseName");	
		String category = request.getParameter("category");
		String attributeSet = request.getParameter("attributeSet");
		String price = request.getParameter("price");
		String specialPrice = request.getParameter("specialPrice");
		String orlPrice = request.getParameter("orlPrice");
		String stockurl = request.getParameter("stockurl");		
		String description = request.getParameter("description");
		if(description!=null){
			description = description.replaceAll("\r\n", "").replaceAll("\r|\n", "").replaceAll(",", " ");
		}
		String sizeDescription = request.getParameter("sizeDescription");
		if(sizeDescription!=null){
			sizeDescription = sizeDescription.replaceAll("\r\n", "").replaceAll("\r|\n", "").replaceAll(",", " ");
		}	
		String brand = request.getParameter("brand");
		String sheinsideColor = request.getParameter("color");
		String[] color = request.getParameterValues("color");
		String material = request.getParameter("material");
		String style = request.getParameter("style");
		String types = request.getParameter("types");
		String item = request.getParameter("item");
		String sleeveLength = request.getParameter("sleeveLength");
		String decoration = request.getParameter("decoration");
		String patternType = request.getParameter("patternType");
		String length = request.getParameter("length");
		String collar = request.getParameter("collar");
		String placket = request.getParameter("placket");
		String fabric = request.getParameter("fabric");
		String silhouette = request.getParameter("silhouette");
		String pantLength = request.getParameter("pantLength");
		String dressesLength = request.getParameter("dressesLength");
		String fitType = request.getParameter("fitType");
		String waistType = request.getParameter("waistType");
		String season = request.getParameter("season");
		String neckline = request.getParameter("neckline");
		
		String gender = request.getParameter("gender");
		String sleeveType = request.getParameter("sleeveType");
		String componentContent = request.getParameter("componentContent");
		String closureType = request.getParameter("closureType");

		
		String editStatus = request.getParameter("editStatus");
		String sizeStatus = request.getParameter("sizeStatus");
		String image = request.getParameter("image");
		String small_image = request.getParameter("smallImage");
		String thumbnail= request.getParameter("thumbnail");
		
		
		Product product = new Product();			
		String returnCode = "000";
		if(productId!=null&&!"".equals(productId)){
			product = productService.get(productId);
			product.setSku(productSku);
			product.setStock(Integer.valueOf(stock));
			product.setSb_shop(productUrl);
			product.setEnglishName(englishName);
			product.setChineseName(chineseName);
			product.setCategory(category);
			product.setAttribute_set(attributeSet);
			product.setPrice(price);
			product.setSpecial_price(specialPrice);
			product.setOrl_price(orlPrice);
			product.setSb_stock_url(stockurl);
			
			product.setDescription(description);
			product.setSizeDescription(sizeDescription);
			product.setSb_brand(brand);
			product.setSb_color(sheinsideColor);
			
			List<ProductOption> product_options = product.getProduct_options();
			for (ProductOption option : product_options) {	
				if(option.getCustom_option_title().equals("Color")){
					List<ProductOptionRow> colorList = option.getOption_rows();
					for(int i=0;i<colorList.size();i++)    {  
						ProductOptionRow optionRow = colorList.get(i);
						int sort = Integer.parseInt(optionRow.getCustom_option_row_sort());
						optionRow.setCustom_option_row_title(color[sort-1]);
					}
					
				}
			}
			
			
			product.setSb_material(material);
			product.setSb_style(style);
			product.setSb_types(types);
			product.setSb_item(item);
			product.setSb_sleeve_length(sleeveLength);
			product.setSb_decoration(decoration);
			product.setSb_pattern_type(patternType);
			product.setSb_length(length);
			product.setSb_collar(collar);
			product.setSb_placket(placket);
			product.setSb_fabric(fabric);
			product.setSb_silhouette(silhouette);
			product.setSb_pant_length(pantLength);
			product.setSb_dresses_length(dressesLength);
			product.setSb_fit_type(fitType);
			product.setSb_waist_type(waistType);
			product.setSb_closure_type(closureType);
			product.setSb_season(season);
			product.setSb_neckline(neckline);
			product.setSb_gender(gender);
			product.setSb_componentContent(componentContent);
			product.setSb_sleeveType(sleeveType);
			if(editStatus!=null){
				product.setEditStatus(Integer.parseInt(editStatus));
			}else{
				product.setEditStatus(0);
			}
			if(sizeStatus!=null){
				product.setSizeStatus(Integer.parseInt(sizeStatus));
			}else{
				product.setSizeStatus(0);
			}
			product.setImage(image);
			product.setSmall_image(small_image);
			product.setThumbnail(thumbnail);
			
			try{
				productService.update(product);
			}catch (Exception e) {
				e.printStackTrace();
				returnCode = e.getMessage();
			}
			
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			product.setCreated_time(new Date());
			product.setSku(productSku);
			product.setStock(Integer.valueOf(stock));
			product.setSb_shop(productUrl);
			product.setEnglishName(englishName);
			product.setChineseName(chineseName);
			product.setCategory(category);
			product.setAttribute_set(attributeSet);
			product.setPrice(price);
			product.setSpecial_price(specialPrice);
			product.setOrl_price(orlPrice);
			product.setSb_stock_url(stockurl);
			product.setDescription(description);
			product.setSizeDescription(sizeDescription);
			product.setSb_brand(brand);
			product.setSb_color(sheinsideColor);
			product.setSb_material(material);
			product.setSb_style(style);
			product.setSb_types(types);
			product.setSb_item(item);
			product.setSb_sleeve_length(sleeveLength);
			product.setSb_decoration(decoration);
			product.setSb_pattern_type(patternType);
			product.setSb_length(length);
			product.setSb_collar(collar);
			product.setSb_placket(placket);
			product.setSb_fabric(fabric);
			product.setSb_silhouette(silhouette);
			product.setSb_pant_length(pantLength);
			product.setSb_dresses_length(dressesLength);
			product.setSb_fit_type(fitType);
			product.setSb_waist_type(waistType);
			product.setSb_closure_type(closureType);
			product.setSb_season(season);
			product.setSb_neckline(neckline);
			product.setSb_gender(gender);
			product.setSb_componentContent(componentContent);
			product.setSb_sleeveType(sleeveType);
			if(editStatus!=null){
				product.setEditStatus(Integer.parseInt(editStatus));
			}else{
				product.setEditStatus(0);
			}	
			if(sizeStatus!=null){
				product.setSizeStatus(Integer.parseInt(sizeStatus));
			}else{
				product.setSizeStatus(0);
			}
			product.setImage(image);
			product.setSmall_image(small_image);
			product.setThumbnail(thumbnail);
			try{
				productService.save(product);
			}catch (Exception e) {
				e.printStackTrace();
				returnCode = e.getMessage();
			}
			
		}
		return new ModelAndView("redirect:/storage/update.do?productId="+product.getId()+"&returnCode="+returnCode);
	}
	

	
	/**
	 * delete batchs
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String productIds = request.getParameter("productIds");
		if(productIds!=null&&!productIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(productIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator productIterator =  aa.iterator();			
			while(productIterator.hasNext()){
				String productId = productIterator.next().toString();
				productService.remove(productId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}
	
	/**
	 * exportSelectProduct
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView exportSelectProduct(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String productIds = request.getParameter("productIds");
		if(productIds!=null&&!productIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(productIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator productIterator =  aa.iterator();	
			List<Product> productList = new ArrayList<Product>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String time = format.format(new Date());
			while(productIterator.hasNext()){
				String productId = productIterator.next().toString();
				Product product = productService.get(productId);			
				productList.add(product);				
			}
			CSVFile.createOutOfStockCSVFile("Category",productList, SystemConfig.File_Path(), "OutOfStock-Select-Product-"+time);			
			
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}
	
	/**
	 * exportProduct
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 * @throws InterruptedException 
	 */
	public ModelAndView exportProduct(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException, InterruptedException {				
		String searchParam = request.getParameter(StorageController.SEARCH_PARAM);
		String searchParamShopsku = request.getParameter(StorageController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(StorageController.SEARCH_PARAM_CATEGORY);
		String searchParamFreeSize = request.getParameter(StorageController.SEARCH_PARAM_FREESIZE);
		String paramStart = request.getParameter(StorageController.SEARCH_PARAM_START);		
		String paramEnd = request.getParameter(StorageController.SEARCH_PARAM_END);
		Product product = new Product();
		product.setStock(0);
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(StorageController.SEARCH_PARAM_SKU)) {
				product.setSku(request.getParameter(StorageController.SEARCH_PARAM_VALUE));								
			}else if(searchParam.equalsIgnoreCase(StorageController.SEARCH_PARAM_NAME)) {
				product.setEnglishName(request.getParameter(StorageController.SEARCH_PARAM_VALUE));				
			}
		}
		if (searchParamShopsku != null){
				product.setShopSku(request.getParameter(StorageController.SEARCH_PARAM_SHOPSKU));				
				
		}
		if (searchParamCategory != null){
			product.setCategory(request.getParameter(StorageController.SEARCH_PARAM_CATEGORY));				
			
		}
		if (searchParamFreeSize != null&&!searchParamFreeSize.equals("")){
			product.setIsFreeSize(Integer.parseInt(request.getParameter(StorageController.SEARCH_PARAM_FREESIZE)));				
			
		}
		if (paramStart != null && !"".equals(paramStart)){
			product.setTime_start(paramStart);			
		}
		if (paramEnd != null && !"".equals(paramEnd)){
			product.setTime_end(paramEnd);
		}
		List<Product> productList = productService.queryProduct(product);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
		String time = format.format(new Date());
		String path = this.getServletContext().getRealPath("/");  
		String fileName ="OutOfStock-"+searchParamShopsku+"-"+searchParamCategory.toLowerCase().replaceAll("/", "-")+"-Product-"+time;
		
		File csvFile = CSVFile.createOutOfStockCSVFile(searchParamCategory,productList, path, fileName);			
		
	    response.setHeader("Content-Disposition","attachment;filename="+fileName+".csv");  
	    response.setContentType("APPLICATION/OCTET-STREAM"); 
	    FileInputStream inputStream = new FileInputStream(csvFile);
	 	OutputStream outputStream = response.getOutputStream();
	    try{  	        	
	        	byte[] buffer = new byte[1024];
	        	int i = -1;
	        	while ((i = inputStream.read(buffer)) != -1) {	
	               outputStream.write(buffer, 0, i);	
	            } 
	        	outputStream.flush();
	        	outputStream.close();
	        	inputStream.close();
	       } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace(); 
	       }finally{  
	            try {  
	                response.getOutputStream().close();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	      }         
		return null;	

	}
	
	/**
	 * delete batchs
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView deleteSearch(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String searchParam = request.getParameter(StorageController.SEARCH_PARAM);
		String searchParamShopsku = request.getParameter(StorageController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(StorageController.SEARCH_PARAM_CATEGORY);
		String searchParamFreeSize = request.getParameter(StorageController.SEARCH_PARAM_FREESIZE);
		String paramStart = request.getParameter(StorageController.SEARCH_PARAM_START);		
		String paramEnd = request.getParameter(StorageController.SEARCH_PARAM_END);
		
		Product product = new Product();
		product.setStock(0);
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(ProductController.SEARCH_PARAM_SKU)) {
				product.setSku(request.getParameter(ProductController.SEARCH_PARAM_VALUE));								
			}else if(searchParam.equalsIgnoreCase(ProductController.SEARCH_PARAM_NAME)) {
				product.setEnglishName(request.getParameter(ProductController.SEARCH_PARAM_VALUE));				
			}
		}
		if (searchParamShopsku != null){
				product.setShopSku(request.getParameter(ProductController.SEARCH_PARAM_SHOPSKU));				
				
		}
		if (searchParamCategory != null){
			product.setCategory(request.getParameter(ProductController.SEARCH_PARAM_CATEGORY));				
			
		}
		if (searchParamFreeSize != null&&!searchParamFreeSize.equals("")){
			product.setIsFreeSize(Integer.parseInt(request.getParameter(ProductController.SEARCH_PARAM_FREESIZE)));				
		
		}
		if (paramStart != null && !"".equals(paramStart)){
			product.setTime_start(paramStart);			
		}
		if (paramEnd != null && !"".equals(paramEnd)){
			product.setTime_end(paramEnd);			
		}
		List<Product> productList = productService.queryProduct(product);
		System.out.println("--------Storage Size:"+productList.size());
		for(Product p:productList){
			productService.remove(p.getId().toString());
		}		
		
		String returnCode = "000";
		StringBuffer saveSuccessStr = new StringBuffer();
		saveSuccessStr.append("<html><head></head><body>");
		saveSuccessStr.append("<textarea>");
		saveSuccessStr.append("{returnCode:'" + returnCode + "'}");
		saveSuccessStr.append("</textarea>");
		saveSuccessStr.append("</body></html>");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(saveSuccessStr.toString());
		return null;

	}
	
}