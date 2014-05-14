package com.shebuyit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.shebuyit.po.Shop;
import com.shebuyit.service.IProductService;
import com.shebuyit.service.IShopService;
import com.shebuyit.vo.DojoGridVO;

public class QuickController extends BaseMultiActionController{	
	private IProductService productService;
	
	private IShopService shopService;
	
	private String quickSearchView;
	
	private String quickView;
		
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

	

	public String getQuickSearchView() {
		return quickSearchView;
	}

	public void setQuickSearchView(String quickSearchView) {
		this.quickSearchView = quickSearchView;
	}

	public String getQuickView() {
		return quickView;
	}

	public void setQuickView(String quickView) {
		this.quickView = quickView;
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

		String searchParam = request.getParameter(QuickController.SEARCH_PARAM);
		String searchParamShopsku = request.getParameter(QuickController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(QuickController.SEARCH_PARAM_CATEGORY);
		String searchParamFreeSize = request.getParameter(QuickController.SEARCH_PARAM_FREESIZE);
		String paramStart = request.getParameter(QuickController.SEARCH_PARAM_START);		
		String paramEnd = request.getParameter(QuickController.SEARCH_PARAM_END);
		Product product = new Product();
		product.setStock(1);
		String defaultSortBy = "id";
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(QuickController.SEARCH_PARAM_SKU)) {
				product.setSku(request.getParameter(QuickController.SEARCH_PARAM_VALUE));				
				queryCondition.append("&"+QuickController.SEARCH_PARAM+"="+QuickController.SEARCH_PARAM_SKU+"&"+QuickController.SEARCH_PARAM_SKU+"=" + request.getParameter(ProductController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(QuickController.SEARCH_PARAM_ORLSKU)) {
				product.setProductSku(request.getParameter(QuickController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+QuickController.SEARCH_PARAM+"="+QuickController.SEARCH_PARAM_ORLSKU+"&"+QuickController.SEARCH_PARAM_VALUE+"=" + request.getParameter(ProductController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(QuickController.SEARCH_PARAM_NAME)) {
				product.setEnglishName(request.getParameter(QuickController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+QuickController.SEARCH_PARAM+"="+QuickController.SEARCH_PARAM_NAME+"&"+QuickController.SEARCH_PARAM_NAME+"=" + request.getParameter(ProductController.SEARCH_PARAM_VALUE));
			}
		}
		if (searchParamShopsku != null){
				product.setShopSku(request.getParameter(QuickController.SEARCH_PARAM_SHOPSKU));				
				queryCondition.append("&"+QuickController.SEARCH_PARAM_SHOPSKU+"=" + request.getParameter(QuickController.SEARCH_PARAM_SHOPSKU));
			
		}
		if (searchParamCategory != null){
			product.setCategory(request.getParameter(QuickController.SEARCH_PARAM_CATEGORY));				
			queryCondition.append("&"+QuickController.SEARCH_PARAM_CATEGORY+"=" + request.getParameter(QuickController.SEARCH_PARAM_CATEGORY));
		
		}
		if (searchParamFreeSize != null&&!searchParamFreeSize.equals("")){
			product.setIsFreeSize(Integer.parseInt(request.getParameter(QuickController.SEARCH_PARAM_FREESIZE)));				
			queryCondition.append("&"+QuickController.SEARCH_PARAM_FREESIZE+"=" + request.getParameter(QuickController.SEARCH_PARAM_FREESIZE));
		
		}
		if (paramStart != null && !"".equals(paramStart)){
			product.setTime_start(paramStart);
			queryCondition.append("&"+QuickController.SEARCH_PARAM_START+"=" + request.getParameter(QuickController.SEARCH_PARAM_START));			
		}
		if (paramEnd != null && !"".equals(paramEnd)){
			product.setTime_end(paramEnd);
			queryCondition.append("&"+QuickController.SEARCH_PARAM_END+"=" + request.getParameter(QuickController.SEARCH_PARAM_END));			
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(quickSearchView);
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
	
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(quickView);
		return mv;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(quickView);
		String productId = request.getParameter("productId");
		String returnCode = request.getParameter("returnCode");
		Product product = productService.get(productId);
		mv.addObject("product", product);
		mv.addObject("returnCode", returnCode);
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
		///request.setCharacterEncoding("UTF-8");
		String productId = request.getParameter("productId");
		String productSku = request.getParameter("productSku");
		String productUrl = request.getParameter("productUrl");
		String englishName = request.getParameter("englishName");
		String chineseName = request.getParameter("chineseName");	
		String category = request.getParameter("category");
		String attributeSet = request.getParameter("attributeSet");
		String price = request.getParameter("price");
		String specialPrice = request.getParameter("specialPrice");
		String description = request.getParameter("description");
		if(description!=null){
			description = description.replaceAll("\r\n", "").replaceAll("\r|\n", "").replaceAll(",", " ");
		}
		String sizeDescription = request.getParameter("sizeDescription");
		if(sizeDescription!=null){
			sizeDescription = sizeDescription.replaceAll("\r\n", "").replaceAll("\r|\n", "").replaceAll(",", " ");
		}	
		String brand = request.getParameter("brand");
		String color = request.getParameter("color");
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
		String closureType = request.getParameter("closureType");
		String season = request.getParameter("season");
		String neckline = request.getParameter("neckline");
		
		
		Product product = new Product();			
		String returnCode = "000";
		if(productId!=null&&!"".equals(productId)){
			product = productService.get(productId);
			product.setSku(productSku);
			product.setSb_shop(productUrl);
			product.setEnglishName(englishName);
			product.setChineseName(chineseName);
			product.setCategory(category);
			product.setAttribute_set(attributeSet);
			product.setPrice(price);
			product.setSpecial_price(specialPrice);
			product.setDescription(description);
			product.setSizeDescription(sizeDescription);
			product.setSb_brand(brand);
			product.setSb_color(color);
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
			product.setSb_shop(productUrl);
			product.setEnglishName(englishName);
			product.setChineseName(chineseName);
			product.setCategory(category);
			product.setAttribute_set(attributeSet);
			product.setPrice(price);
			product.setSpecial_price(specialPrice);
			product.setDescription(description);
			product.setSizeDescription(sizeDescription);
			product.setSb_brand(brand);
			product.setSb_color(color);
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
			try{
				productService.save(product);
			}catch (Exception e) {
				e.printStackTrace();
				returnCode = e.getMessage();
			}
			
		}		
		return new ModelAndView("redirect:/product/update.do?productId="+productId+"&returnCode="+returnCode);
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
			while(productIterator.hasNext()){
				String productId = productIterator.next().toString();
				Product product = productService.get(productId);
				List<Product> productList = new ArrayList<Product>();
				productList.add(product);
				CSVFile.createCSVFile("Category",productList, SystemConfig.File_Path(), product.getShopSku()+"-"+product.getCategory().toLowerCase().replaceAll("/", "-")+"-Product-"+product.getSku());			
				
			}
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
		String searchParam = request.getParameter(ProductController.SEARCH_PARAM);
		String searchParamShopsku = request.getParameter(ProductController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(ProductController.SEARCH_PARAM_CATEGORY);
		Product product = new Product();
		product.setStock(1);
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
		List<Product> productList = productService.queryProduct(product);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
		String time = format.format(new Date());
		CSVFile.createCSVFile(searchParamCategory,productList, SystemConfig.File_Path(), searchParamShopsku+"-"+searchParamCategory.toLowerCase().replaceAll("/", "-")+"-Product-"+time);			
		
				
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
	
	/**
	 * updateName
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 * @throws InterruptedException 
	 */
	public ModelAndView updateName(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException, InterruptedException {				
		String productId = request.getParameter("productId");
		String englishName = request.getParameter("englishName");
		Product product =  productService.get(productId);
		product.setEditStatus(1);
		product.setEnglishName(englishName);		
		productService.update(product);
						
		response.getWriter().print("000");
		return null;

	}
	
	/**
	 * updateProducts
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 * @throws InterruptedException 
	 */
	public ModelAndView updateProducts(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException, InterruptedException {				
		String[] productIds = request.getParameterValues("productIds");
		String[] englishNames = request.getParameterValues("englishName");
		
		for(int i =0;i<productIds.length;i++){
			Product product =  productService.get(productIds[i]);
			product.setEditStatus(1);
			product.setEnglishName(englishNames[i]);		
			productService.update(product);
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