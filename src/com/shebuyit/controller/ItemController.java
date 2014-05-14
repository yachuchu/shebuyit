package com.shebuyit.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.ibm.json.java.JSONArray;
import com.shebuyit.crawler.util.DataDictionary;
import com.shebuyit.po.OrderItem;
import com.shebuyit.service.IOrderItemService;
import com.shebuyit.service.IProductService;
import com.shebuyit.vo.DojoGridVO;

public class ItemController extends BaseMultiActionController{	
	private IProductService productService;
	
	private IOrderItemService orderItemService;
	
	private String itemSearchView;
	
	private String itemView;
		
	public static String SEARCH_PARAM = "search_param";
		
	public static String SEARCH_PARAM_VALUE = "param_value";
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	public static String SEARCH_PARAM_SKU = "sku";
	
	public static String SEARCH_PARAM_TAOBAOORDERNUMBER = "taobaoOrderNumber";
	
	public static String SEARCH_PARAM_ORDERNUMBER = "orderNumber";
	
	
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public IOrderItemService getOrderItemService() {
		return orderItemService;
	}

	public void setOrderItemService(IOrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}

	public String getItemSearchView() {
		return itemSearchView;
	}

	public void setItemSearchView(String itemSearchView) {
		this.itemSearchView = itemSearchView;
	}

	public String getItemView() {
		return itemView;
	}

	public void setItemView(String itemView) {
		this.itemView = itemView;
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

		String searchParam = request.getParameter(ItemController.SEARCH_PARAM);
		String paramStart = request.getParameter(ItemController.SEARCH_PARAM_START);		
		String paramEnd = request.getParameter(ItemController.SEARCH_PARAM_END);
		OrderItem orderItem = new OrderItem();
		String defaultSortBy = "id";
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(ItemController.SEARCH_PARAM_SKU)) {
				orderItem.setSku(request.getParameter(ItemController.SEARCH_PARAM_VALUE));				
				queryCondition.append("&"+ItemController.SEARCH_PARAM+"="+ItemController.SEARCH_PARAM_SKU+"&"+ItemController.SEARCH_PARAM_VALUE+"=" + request.getParameter(ItemController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(ItemController.SEARCH_PARAM_ORDERNUMBER)) {
				orderItem.setOrderNumber(request.getParameter(ItemController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+ItemController.SEARCH_PARAM+"="+ItemController.SEARCH_PARAM_ORDERNUMBER+"&"+ItemController.SEARCH_PARAM_VALUE+"=" + request.getParameter(ItemController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(ItemController.SEARCH_PARAM_TAOBAOORDERNUMBER)) {
				orderItem.setTaobao_order_number(request.getParameter(ItemController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+ItemController.SEARCH_PARAM+"="+ItemController.SEARCH_PARAM_TAOBAOORDERNUMBER+"&"+ItemController.SEARCH_PARAM_VALUE+"=" + request.getParameter(ItemController.SEARCH_PARAM_VALUE));
			}
			
		}	
		if (paramStart != null && !"".equals(paramStart)){
			orderItem.setTime_start(paramStart);
			queryCondition.append("&"+ItemController.SEARCH_PARAM_START+"=" + request.getParameter(ItemController.SEARCH_PARAM_START));			
		}
		if (paramEnd != null && !"".equals(paramEnd)){
			orderItem.setTime_end(paramEnd);
			queryCondition.append("&"+ItemController.SEARCH_PARAM_END+"=" + request.getParameter(ItemController.SEARCH_PARAM_END));			
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(itemSearchView);
		try {
			String sortby = request.getParameter("sortby");
			String orderby = request.getParameter("order");
			boolean isAsc = false;
			if (!(sortby != null && sortby.trim().length() != 0)) {
				if(defaultSortBy == null)
					sortby = "id";
				else
					sortby = defaultSortBy;
			}
			if (orderby != null && orderby.equals("ASC")) {
				isAsc = true;
			}
			List<OrderItem> orderItems = orderItemService.queryOrderItemByQueryCondition(orderItem, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(orderItems);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),orderItems, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	
	
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(itemView);
		String orderItemId = request.getParameter("orderItemId");
		String returnCode = request.getParameter("returnCode");
		OrderItem orderItem = orderItemService.get(orderItemId);
		mv.addObject("item", orderItem);
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
	 * @throws ParseException 
	 */
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws ServletException, IOException, ParseException {				
		///request.setCharacterEncoding("UTF-8");
		String orderItemId = request.getParameter("orderItemId");
		String sku = request.getParameter("sku");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity").equals("")?"0":request.getParameter("quantity");
		String taobao_order_number = request.getParameter("taobao_order_number");
		String taobao_price = request.getParameter("taobao_price").equals("")?"0":request.getParameter("taobao_price");	
				
		OrderItem orderItem = new OrderItem();			
		String returnCode = "000";
		if(orderItemId!=null&&!"".equals(orderItemId)){
			orderItem = orderItemService.get(orderItemId);
			orderItem.setSku(sku);
			orderItem.setPrice(Double.parseDouble(price));
			orderItem.setQuantity(Integer.parseInt(quantity));			
			orderItem.setTaobao_price(Double.parseDouble(taobao_price));
			
			BigDecimal bdPrice=  new BigDecimal(price);
			BigDecimal bdTaobaoPrice=  new BigDecimal(taobao_price);
			
			BigDecimal bdProfit = bdPrice.subtract(bdTaobaoPrice);			
			BigDecimal bdProfitRate =bdProfit.divide(bdPrice,4,BigDecimal.ROUND_HALF_EVEN);
			
			orderItem.setProfit(bdProfit.doubleValue());
			orderItem.setProfitRate(bdProfitRate.doubleValue());
			
			 
			orderItem.setTaobao_order_number(taobao_order_number);															
			try{
				orderItemService.update(orderItem);
			}catch (Exception e) {
				e.printStackTrace();
				returnCode = e.getMessage();
			}
			
		}		
		return new ModelAndView("redirect:/item/update.do?orderItemId="+orderItem.getId()+"&returnCode="+returnCode);
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
		String orderItemIds = request.getParameter("orderItemIds");
		if(orderItemIds!=null&&!orderItemIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(orderItemIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator orderItemIterator =  aa.iterator();			
			while(orderItemIterator.hasNext()){
				String orderItemId = orderItemIterator.next().toString();
				orderItemService.remove(orderItemId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}

	
}