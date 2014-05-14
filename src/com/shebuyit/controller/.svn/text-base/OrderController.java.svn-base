package com.shebuyit.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.ibm.json.java.JSONArray;
import com.shebuyit.crawler.util.DataDictionary;
import com.shebuyit.crawler.util.DataUtils;
import com.shebuyit.po.OrderItem;
import com.shebuyit.po.Orders;
import com.shebuyit.po.Product;
import com.shebuyit.service.IOrderItemService;
import com.shebuyit.service.IOrderService;
import com.shebuyit.service.IProductService;
import com.shebuyit.vo.DojoGridVO;

public class OrderController extends BaseMultiActionController{	
	private IProductService productService;
	
	private IOrderService orderService;
	
	private IOrderItemService orderItemService;
	
	private String orderSearchView;
	
	private String orderView;
	
	private String orderItemView;
		
	public static String SEARCH_PARAM = "search_param";
		
	public static String SEARCH_PARAM_VALUE = "param_value";
	
	public static String SEARCH_PARAM_ORDERNUMBER = "orderNumber";
	
	public static String SEARCH_PARAM_ORDERNUMBER4PX = "orderNumber4px";
	
	public static String SEARCH_PARAM_SHIPCHANNEL = "shipchannel";
	
	public static String SEARCH_PARAM_DESTINATION = "destination";
	
	public static String SEARCH_PARAM_SITE = "site";
	
	
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	
	
	
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}


	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}


	public IOrderItemService getOrderItemService() {
		return orderItemService;
	}

	public void setOrderItemService(IOrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}

	public String getOrderSearchView() {
		return orderSearchView;
	}

	public void setOrderSearchView(String orderSearchView) {
		this.orderSearchView = orderSearchView;
	}

	public String getOrderView() {
		return orderView;
	}

	public void setOrderView(String orderView) {
		this.orderView = orderView;
	}

	public String getOrderItemView() {
		return orderItemView;
	}

	public void setOrderItemView(String orderItemView) {
		this.orderItemView = orderItemView;
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
		Map shipChannelMap = DataUtils.shipChannelMap();
		Map destinationMap = DataUtils.destinationMap();
		Map siteMap = DataUtils.siteMap();
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

		String searchParam = request.getParameter(OrderController.SEARCH_PARAM);
		String searchParamDestination = request.getParameter(OrderController.SEARCH_PARAM_DESTINATION);
		String searchParamShipchannel = request.getParameter(OrderController.SEARCH_PARAM_SHIPCHANNEL);
		String searchParamSite = request.getParameter(OrderController.SEARCH_PARAM_SITE);
		
		String paramStart = request.getParameter(OrderController.SEARCH_PARAM_START);		
		String paramEnd = request.getParameter(OrderController.SEARCH_PARAM_END);
		Orders order = new Orders();
		String defaultSortBy = "id";
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(OrderController.SEARCH_PARAM_ORDERNUMBER)) {
				order.setOrderNumber(request.getParameter(OrderController.SEARCH_PARAM_VALUE));				
				queryCondition.append("&"+OrderController.SEARCH_PARAM+"="+OrderController.SEARCH_PARAM_ORDERNUMBER+"&"+OrderController.SEARCH_PARAM_VALUE+"=" + request.getParameter(OrderController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(OrderController.SEARCH_PARAM_ORDERNUMBER4PX)) {
				order.setOrderNumber4px(request.getParameter(OrderController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+OrderController.SEARCH_PARAM+"="+OrderController.SEARCH_PARAM_ORDERNUMBER4PX+"&"+OrderController.SEARCH_PARAM_VALUE+"=" + request.getParameter(OrderController.SEARCH_PARAM_VALUE));
			}
			
		}
		if (searchParamShipchannel != null){
			order.setShipChannel(request.getParameter(OrderController.SEARCH_PARAM_SHIPCHANNEL));				
			queryCondition.append("&"+OrderController.SEARCH_PARAM_SHIPCHANNEL+"=" + request.getParameter(OrderController.SEARCH_PARAM_SHIPCHANNEL));
		
		}
		if (searchParamDestination != null){
			order.setDestination(request.getParameter(OrderController.SEARCH_PARAM_DESTINATION));				
			queryCondition.append("&"+OrderController.SEARCH_PARAM_DESTINATION+"=" + request.getParameter(OrderController.SEARCH_PARAM_DESTINATION));
		
		}
		if (searchParamSite != null){
			order.setSite(request.getParameter(OrderController.SEARCH_PARAM_SITE));				
			queryCondition.append("&"+OrderController.SEARCH_PARAM_SITE+"=" + request.getParameter(OrderController.SEARCH_PARAM_SITE));
		
		}
		if (paramStart != null && !"".equals(paramStart)){
			order.setTime_start(paramStart);
			queryCondition.append("&"+OrderController.SEARCH_PARAM_START+"=" + request.getParameter(OrderController.SEARCH_PARAM_START));			
		}
		if (paramEnd != null && !"".equals(paramEnd)){
			order.setTime_end(paramEnd);
			queryCondition.append("&"+OrderController.SEARCH_PARAM_END+"=" + request.getParameter(OrderController.SEARCH_PARAM_END));			
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(orderSearchView);
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
			List<Orders> orders = orderService.queryOrderByQueryCondition(order, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(orders);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),orders, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject("shipChannelMap", shipChannelMap);
			mv.addObject("destinationMap", destinationMap);
			mv.addObject("siteMap", siteMap);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	
	
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(orderView);
		Map shipChannelMap = DataUtils.shipChannelMap();
		Map destinationMap = DataUtils.destinationMap();
		Map siteMap = DataUtils.siteMap();
		mv.addObject("shipChannelMap", shipChannelMap);
		mv.addObject("destinationMap", destinationMap);
		mv.addObject("siteMap", siteMap);
		return mv;
	}
	
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(orderView);
		String orderId = request.getParameter("orderId");
		String returnCode = request.getParameter("returnCode");
		Orders order = orderService.get(orderId);
		List<OrderItem> orderItemList = order.getOrder_items();
		Map shipChannelMap = DataUtils.shipChannelMap();
		Map destinationMap = DataUtils.destinationMap();
		Map siteMap = DataUtils.siteMap();
		mv.addObject("shipChannelMap", shipChannelMap);
		mv.addObject("destinationMap", destinationMap);
		mv.addObject("siteMap", siteMap);
		mv.addObject("order", order);
		mv.addObject("returnCode", returnCode);
		mv.addObject("orderItemList", orderItemList);
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
		String orderId = request.getParameter("orderId");
		String orderNumber = request.getParameter("orderNumber");
		String orderNumber4px = request.getParameter("orderNumber4px");	
		String site = request.getParameter("site");
		String subtotal = request.getParameter("subtotal").equals("")?"0":request.getParameter("subtotal");
		String discount = request.getParameter("discount").equals("")?"0":request.getParameter("discount");
		String shipStatus = request.getParameter("shipStatus");
		String shipping = request.getParameter("shipping").equals("")?"0":request.getParameter("shipping");	
		String shipChannel = request.getParameter("shipChannel");
		String destination = request.getParameter("destination");
		String city = request.getParameter("city");		
		String shipWeight = request.getParameter("shipWeight");
		String shipPrice = request.getParameter("shipPrice").equals("")?"0":request.getParameter("shipPrice");
		String shipTime_start = request.getParameter("shipTime_start");
		String shipTime_end = request.getParameter("shipTime_end");
					
		Orders order = new Orders();			
		String returnCode = "000";
		if(orderId!=null&&!"".equals(orderId)){
			order = orderService.get(orderId);
			order.setOrderNumber(orderNumber);
			order.setOrderNumber4px(orderNumber4px);
			order.setSite(site);
			order.setSubtotal(Double.parseDouble(subtotal));
			order.setDiscount(Double.parseDouble(discount));			
			order.setShipping(Double.parseDouble(shipping));
			
			BigDecimal bdSubtotal=  new BigDecimal(subtotal);
			BigDecimal bdShipping=  new BigDecimal(shipping);
			BigDecimal bdDiscount=  new BigDecimal(discount);
			order.setTotalDue((bdSubtotal.add(bdShipping).subtract(bdDiscount)).doubleValue());
			
			order.setShipChannel(shipChannel);
			order.setDestination(destination);
			order.setCity(city);
			order.setShipWeight(shipWeight);
			order.setShipPrice(Double.parseDouble(shipPrice));
			order.setShipTime_start(shipTime_start);
			order.setShipTime_end(shipTime_end);
			order.setDollarRate(DataDictionary.getDollarRate());
			if(shipStatus!=null){
				order.setShipStatus(Integer.parseInt(shipStatus));
			}else{
				order.setShipStatus(0);
			}
																
			try{
				orderService.update(order);
			}catch (Exception e) {
				e.printStackTrace();
				returnCode = e.getMessage();
			}
			
		}else{			
			order.setOrderNumber(orderNumber);
			order.setOrderNumber4px(orderNumber4px);
			order.setSite(site);
			order.setSubtotal(Double.parseDouble(subtotal));
			order.setDiscount(Double.parseDouble(discount));
			order.setShipping(Double.parseDouble(shipping));
			
			BigDecimal bdSubtotal=  new BigDecimal(subtotal);
			BigDecimal bdShipping=  new BigDecimal(shipping);
			BigDecimal bdDiscount=  new BigDecimal(discount);
			order.setTotalDue((bdSubtotal.add(bdShipping).subtract(bdDiscount)).doubleValue());
			
			order.setShipChannel(shipChannel);
			order.setDestination(destination);
			order.setCity(city);
			order.setShipWeight(shipWeight);
			order.setShipPrice(Double.parseDouble(shipPrice));
			order.setCreated_time(new Date());
			order.setShipTime_start(shipTime_start);
			order.setShipTime_end(shipTime_end);
			order.setDollarRate(DataDictionary.getDollarRate());
			if(shipStatus!=null){
				order.setShipStatus(Integer.parseInt(shipStatus));
			}else{
				order.setShipStatus(0);
			}
			try{
				orderService.save(order);
			}catch (Exception e) {
				e.printStackTrace();
				returnCode = e.getMessage();
			}
			
		}		
		return new ModelAndView("redirect:/order/update.do?orderId="+order.getId()+"&returnCode="+returnCode);
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
		String orderIds = request.getParameter("orderIds");
		if(orderIds!=null&&!orderIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(orderIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator orderIterator =  aa.iterator();			
			while(orderIterator.hasNext()){
				String orderId = orderIterator.next().toString();
				orderService.remove(orderId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}
	
	
	public ModelAndView addItem(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(orderItemView);
		String orderId = request.getParameter("orderId");
		mv.addObject("orderId", orderId);
		return mv;
	}
	
	public ModelAndView updateItem(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(orderItemView);
		String orderItemId = request.getParameter("orderItemId");
		OrderItem orderItem = orderItemService.get(orderItemId);
		String orderId = orderItem.getOrders().getId().toString();
		mv.addObject("orderId", orderId);
		mv.addObject("item", orderItem);
		return mv;
	}
	
	
	public ModelAndView saveItem(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws ServletException, IOException, ParseException {				
		///request.setCharacterEncoding("UTF-8");
		String orderId = request.getParameter("orderId");
		String orderItemId = request.getParameter("orderItemId");
		String sku = request.getParameter("sku");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity").equals("")?"1":request.getParameter("quantity");
		String taobao_order_number = request.getParameter("taobao_order_number");
		String taobao_price = request.getParameter("taobao_price").equals("")?"0":request.getParameter("taobao_price");	
		
		
		
				
		OrderItem orderItem = new OrderItem();	
		if(price==null||price.length()==0){
			Product product = new Product();
			product.setSku(sku);
			List<Product> productList = productService.queryProduct(product);
			if(productList!=null&&productList.size()>0){
				Product p = productList.get(0);
				price = p.getSpecial_price();	
				int saleCount = p.getSaleCount();
				saleCount = saleCount+Integer.parseInt(quantity);
				p.setSaleCount(saleCount);
				productService.update(p);
			}
		}
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
			
		}else{	
			Orders order = orderService.get(orderId);
			orderItem.setOrderNumber(order.getOrderNumber());
			orderItem.setSku(sku);
			orderItem.setCreated_time(new Date());
			orderItem.setPrice(Double.parseDouble(price)*order.getDollarRate());
			orderItem.setQuantity(Integer.parseInt(quantity));			
			orderItem.setTaobao_price(Double.parseDouble(taobao_price));
			
			BigDecimal bdPrice=  new BigDecimal(price);
			BigDecimal bdDollarRate=  new BigDecimal(DataDictionary.getDollarRate()+"");
			
			BigDecimal bdPriceRmb = bdPrice.multiply(bdDollarRate);
			BigDecimal bdTaobaoPrice=  new BigDecimal(taobao_price);
			
			
			
			BigDecimal bdProfit = bdPriceRmb.subtract(bdTaobaoPrice);			
			BigDecimal bdProfitRate =bdProfit.divide(bdPriceRmb,4,BigDecimal.ROUND_HALF_EVEN);
			
			orderItem.setProfit(bdProfit.doubleValue());
			orderItem.setProfitRate(bdProfitRate.doubleValue());
			
			orderItem.setTaobao_order_number(taobao_order_number);
			orderItem.setOrders(order);
			try{
				orderItemService.save(orderItem);
			}catch (Exception e) {
				e.printStackTrace();
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
	 * delete batchs
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView deleteItem(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String orderItemId = request.getParameter("orderItemId");
		
		orderItemService.remove(orderItemId);

		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}
	
	

	
}