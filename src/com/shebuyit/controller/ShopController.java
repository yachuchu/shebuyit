package com.shebuyit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.shebuyit.crawler.jsoup.Crawler;
import com.shebuyit.crawler.parser.SammydressCrawler;
import com.shebuyit.crawler.parser.SheinsideCrawler;
import com.shebuyit.crawler.parser.StockCrawler;
import com.shebuyit.crawler.parser.TaobaoCrawler;
import com.shebuyit.crawler.parser.TianmaoCrawler;
import com.shebuyit.crawler.parser.TianmaoJewelryCrawler;
import com.shebuyit.crawler.parser.VanclCrawler;
import com.shebuyit.crawler.util.DataUtils;
import com.shebuyit.po.Product;
import com.shebuyit.po.Shop;
import com.shebuyit.service.IProductService;
import com.shebuyit.service.IShopService;
import com.shebuyit.vo.DojoGridVO;

public class ShopController extends BaseMultiActionController{	
	private IShopService shopService;
	
	private IProductService productService;
	
	private String shopSearchView;
	
	private String shopView;
		
	public static String SEARCH_PARAM = "search_param";
	
	public static String SEARCH_PARAM_SHOPSKU = "shopSku";
	
	public static String SEARCH_PARAM_SHOPBRAND = "shopBrand";
	
	public static String SEARCH_PARAM_VALUE = "param_value";
	
	public static String SEARCH_PARAM_CATEGORY = "category";
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	public IShopService getShopService() {
		return shopService;
	}

	public void setShopService(IShopService shopService) {
		this.shopService = shopService;
	}
	
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
	public String getShopSearchView() {
		return shopSearchView;
	}

	public void setShopSearchView(String shopSearchView) {
		this.shopSearchView = shopSearchView;
	}

	public String getShopView() {
		return shopView;
	}

	public void setShopView(String shopView) {
		this.shopView = shopView;
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
		List<Shop> shopList = shopService.queryShop(new Shop());
		Map<String,String> shopSkuMap = new HashMap<String,String>();
		for(Shop shop:shopList){
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
			dojoGridVO.getPaging().setSize(20);
			dojoGridVO.getPaging().setCurrent(currentPageNum);
		}

		String searchParamShopsku = request.getParameter(ShopController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(ShopController.SEARCH_PARAM_CATEGORY);
		Shop shop = new Shop();
		
		String defaultSortBy = "id";;
		
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParamShopsku != null){
			shop.setShopSku(request.getParameter(ShopController.SEARCH_PARAM_SHOPSKU));				
			queryCondition.append("&"+ShopController.SEARCH_PARAM_SHOPSKU+"=" + request.getParameter(ShopController.SEARCH_PARAM_SHOPSKU));		
		}
		if (searchParamCategory != null){
			shop.setCategory(request.getParameter(ShopController.SEARCH_PARAM_CATEGORY));				
			queryCondition.append("&"+ShopController.SEARCH_PARAM_CATEGORY+"=" + request.getParameter(ShopController.SEARCH_PARAM_CATEGORY));
		
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(shopSearchView);
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
			List<Shop> shops = shopService.queryShopByQueryCondition(shop, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(shops);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),shops, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("shopSkuMap", shopSkuMap);
		mv.addObject("categoryMap", categoryMap);
		return mv;
	}	
	
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(shopView);
		Map categoryMap = DataUtils.categoryMap();
		Map shopCanalMap = DataUtils.shopCanalMap();
		Map attributeSetMap = DataUtils.attributeSetMap();
		mv.addObject("categoryMap", categoryMap);
		mv.addObject("shopCanalMap", shopCanalMap);
		mv.addObject("attributeSetMap", attributeSetMap);
		return mv;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(shopView);
		Map categoryMap = DataUtils.categoryMap();
		Map shopCanalMap = DataUtils.shopCanalMap();
		Map attributeSetMap = DataUtils.attributeSetMap();
		String shopId = request.getParameter("shopId");
		Shop shop = shopService.get(shopId);
		mv.addObject("shop", shop);
		mv.addObject("categoryMap", categoryMap);
		mv.addObject("shopCanalMap", shopCanalMap);
		mv.addObject("attributeSetMap", attributeSetMap);
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
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) throws ServletException, IOException {			
		String shopId = request.getParameter("shopId");
		String shopSku = request.getParameter("shopSku");
		String shopUrl = request.getParameter("shopUrl");
		String shopName = request.getParameter("shopName");
		String category = request.getParameter("category");
		String attributeSet = request.getParameter("attributeSet");
		String shopBrand = request.getParameter("shopBrand");
		String description = request.getParameter("description");
		String shopCanal = request.getParameter("shopCanal");
		
		String material = request.getParameter("material");
		String style = request.getParameter("style");
		String types = request.getParameter("types");
		String season = request.getParameter("season");
		String item = request.getParameter("item");
		String neckline = request.getParameter("neckline");
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

		
		Shop shop = new Shop();
		String returnCode = "000";
		if(shopId!=null&&!"".equals(shopId)){
			shop = shopService.get(shopId);
			shop.setShopSku(shopSku);
			shop.setDescription(description);
			shop.setShopUrl(shopUrl);
			shop.setShopBrand(shopBrand);
			shop.setShopName(shopName);
			shop.setCategory(category);
			shop.setAttribute_set(attributeSet);
			shop.setShopCanal(shopCanal);
			
			shop.setSb_material(material);
			shop.setSb_style(style);
			shop.setSb_types(types);
			shop.setSb_item(item);
			shop.setSb_sleeve_length(sleeveLength);
			shop.setSb_decoration(decoration);
			shop.setSb_pattern_type(patternType);
			shop.setSb_length(length);
			shop.setSb_collar(collar);
			shop.setSb_placket(placket);
			shop.setSb_fabric(fabric);
			shop.setSb_silhouette(silhouette);
			shop.setSb_pant_length(pantLength);
			shop.setSb_dresses_length(dressesLength);
			shop.setSb_fit_type(fitType);
			shop.setSb_waist_type(waistType);
			shop.setSb_closure_type(closureType);
			shop.setSb_season(season);
			shop.setSb_neckline(neckline);
			try{
				shopService.update(shop);
			}catch (Exception e) {
				//e.printStackTrace();
				returnCode = e.getMessage();
			}
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			shop.setCreated_time(format.format(new Date()));
			shop.setShopSku(shopSku);
			shop.setDescription(description);
			shop.setShopUrl(shopUrl);
			shop.setShopBrand(shopBrand);
			shop.setShopName(shopName);
			shop.setCategory(category);
			shop.setAttribute_set(attributeSet);
			shop.setShopCanal(shopCanal);
			
			shop.setSb_material(material);
			shop.setSb_style(style);
			shop.setSb_types(types);
			shop.setSb_item(item);
			shop.setSb_sleeve_length(sleeveLength);
			shop.setSb_decoration(decoration);
			shop.setSb_pattern_type(patternType);
			shop.setSb_length(length);
			shop.setSb_collar(collar);
			shop.setSb_placket(placket);
			shop.setSb_fabric(fabric);
			shop.setSb_silhouette(silhouette);
			shop.setSb_pant_length(pantLength);
			shop.setSb_dresses_length(dressesLength);
			shop.setSb_fit_type(fitType);
			shop.setSb_waist_type(waistType);
			shop.setSb_closure_type(closureType);
			shop.setSb_season(season);
			shop.setSb_neckline(neckline);
			try{
				shopService.save(shop);
			}catch (Exception e) {
				//e.printStackTrace();
				returnCode = e.getMessage();
			}
		}	
		
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
	 * delete shops
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String shopIds = request.getParameter("shopIds");
		if(shopIds!=null&&!shopIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(shopIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator shopIterator =  aa.iterator();			
			while(shopIterator.hasNext()){
				String shopId = shopIterator.next().toString();
				shopService.remove(shopId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null;

	}
	
	public ModelAndView runCrawler(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) throws IOException {
		String shopId = request.getParameter("shopId");
		Shop shop = shopService.get(shopId);
		if(shop.getShopSku().equals("1001")){
			Crawler sheinsideCrawler = new SheinsideCrawler();
			sheinsideCrawler.setShop(shop);		
			new Thread(sheinsideCrawler).start();
		}else if(shop.getShopSku().equals("1002")){
			Crawler sammydressCrawler = new SammydressCrawler();
			sammydressCrawler.setShop(shop);		
			new Thread(sammydressCrawler).start();
		}else{
			if("taobao".equals(shop.getShopCanal())){
				Crawler taobaoCrawler = new TaobaoCrawler();
				taobaoCrawler.setShop(shop);	
				new Thread(taobaoCrawler).start();
			}else if("tianmao".equals(shop.getShopCanal())){
				Crawler tianmaoCrawler = new TianmaoCrawler();
				tianmaoCrawler.setShop(shop);	
				new Thread(tianmaoCrawler).start();
			}else if("tianmaoJewelry".equals(shop.getShopCanal())){
				Crawler tianmaoJewelryCrawler = new TianmaoJewelryCrawler();
				tianmaoJewelryCrawler.setShop(shop);	
				new Thread(tianmaoJewelryCrawler).start();
			}
			
		}		
		response.getWriter().print("000");
		return null; 
	}
	
	public ModelAndView runSelectCrawler(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IOException {
		ModelAndView mv = new ModelAndView(shopView);
		String shopIds = request.getParameter("shopIds");
		if(shopIds!=null&&!shopIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(shopIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator shopIterator =  aa.iterator();			
			while(shopIterator.hasNext()){
				String shopId = shopIterator.next().toString();
				Shop shop = shopService.get(shopId);
				if(shop.getShopSku().equals("1001")){
					SheinsideCrawler sheinsideCrawler = new SheinsideCrawler();
					sheinsideCrawler.setShop(shop);		
					sheinsideCrawler.doStart(shop);
				}else if(shop.getShopSku().equals("1002")){
					SammydressCrawler sammydressCrawler = new SammydressCrawler();
					sammydressCrawler.setShop(shop);		
					sammydressCrawler.doStart(shop);
				}else{
					if("taobao".equals(shop.getShopCanal())){
						Crawler taobaoCrawler = new TaobaoCrawler();
						taobaoCrawler.setShop(shop);	
						new Thread(taobaoCrawler).start();
					}else if("tianmao".equals(shop.getShopCanal())){
						TianmaoCrawler tianmaoCrawler = new TianmaoCrawler();
						tianmaoCrawler.setShop(shop);	
						tianmaoCrawler.doStart(shop);
					}else if("tianmaoJewelry".equals(shop.getShopCanal())){
						TianmaoJewelryCrawler tianmaoJewelryCrawler = new TianmaoJewelryCrawler();
						tianmaoJewelryCrawler.setShop(shop);	
						tianmaoJewelryCrawler.doStart(shop);
					}
					
				}
			}
		}
		
		response.getWriter().print("000");
		return null; 
	}
	
	
	public ModelAndView runStockCrawler(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) throws IOException {
		String searchParamShopsku = request.getParameter(ShopController.SEARCH_PARAM_SHOPSKU);
		String searchParamCategory = request.getParameter(ShopController.SEARCH_PARAM_CATEGORY);
		
		if (searchParamShopsku != null&&!searchParamShopsku.equals("")){
				StockCrawler stockCrawler = new StockCrawler();
				Product product = new Product();
				product.setStock(1);
				product.setShopSku(searchParamShopsku);
				product.setCategory(searchParamCategory);
				List<Product> productList = productService.queryProduct(product);
				stockCrawler.setProductList(productList);
				new Thread(stockCrawler).start();		
				response.getWriter().print("000");
				return null; 
		}
		response.getWriter().print("001");
		return null;
		
	}
	

	
}
