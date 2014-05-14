package com.shebuyit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.ibm.json.java.JSONArray;
import com.shebuyit.po.Dict;
import com.shebuyit.service.IDictService;
import com.shebuyit.vo.DojoGridVO;

public class DictController  extends BaseMultiActionController{	
	private IDictService dictService;
	
	private String dictSearchView;
	
	private String dictView;
		
	public static String SEARCH_PARAM = "search_param";
	
	public static String SEARCH_PARAM_DICTCODE = "code";
	
	public static String SEARCH_PARAM_DICTNAME = "name";
	
	public static String SEARCH_PARAM_VALUE = "param_value";
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	public IDictService getDictService() {
		return dictService;
	}

	public void setDictService(IDictService dictService) {
		this.dictService = dictService;
	}
	
	public String getDictSearchView() {
		return dictSearchView;
	}

	public void setDictSearchView(String dictSearchView) {
		this.dictSearchView = dictSearchView;
	}

	public String getDictView() {
		return dictView;
	}

	public void setDictView(String dictView) {
		this.dictView = dictView;
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

		String searchParam = request.getParameter(ProductController.SEARCH_PARAM);
		String searchParamCategory = request.getParameter(ProductController.SEARCH_PARAM_CATEGORY);
		String searchParamStart = request.getParameter(ProductController.SEARCH_PARAM_START);
		String searchParamEnd = request.getParameter(ProductController.SEARCH_PARAM_END);
		
		Dict Dict = new Dict();
		
		String defaultSortBy = "id";;
		
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(DictController.SEARCH_PARAM_DICTNAME)) {
				Dict.setName(request.getParameter(DictController.SEARCH_PARAM_VALUE));		
				queryCondition.append("&"+DictController.SEARCH_PARAM+"="+DictController.SEARCH_PARAM_DICTNAME+"&"+DictController.SEARCH_PARAM_DICTNAME+"=" + request.getParameter(DictController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(DictController.SEARCH_PARAM_DICTCODE)) {
				Dict.setCode(request.getParameter(DictController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+DictController.SEARCH_PARAM+"="+DictController.SEARCH_PARAM_DICTCODE+"&"+DictController.SEARCH_PARAM_DICTCODE+"=" + request.getParameter(DictController.SEARCH_PARAM_VALUE));
			}
		}
		if (searchParamStart != null&&searchParamEnd != null){
			Dict.setTime_start(request.getParameter(DictController.SEARCH_PARAM_START));
			Dict.setTime_end(request.getParameter(DictController.SEARCH_PARAM_END));
			queryCondition.append("&"+DictController.SEARCH_PARAM_START+"=" + request.getParameter(DictController.SEARCH_PARAM_START)+"&"+DictController.SEARCH_PARAM_END+"=" + request.getParameter(DictController.SEARCH_PARAM_END));
		
		}
		if (searchParamStart != null&&searchParamEnd == null){
			Dict.setTime_start(request.getParameter(DictController.SEARCH_PARAM_START));				
			queryCondition.append("&"+DictController.SEARCH_PARAM_START+"=" + request.getParameter(DictController.SEARCH_PARAM_START));
		
		}
		if (searchParamStart == null&&searchParamEnd != null){
			Dict.setTime_end(request.getParameter(DictController.SEARCH_PARAM_END));				
			queryCondition.append("&"+DictController.SEARCH_PARAM_END+"=" + request.getParameter(DictController.SEARCH_PARAM_END));
		
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(dictSearchView);
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
			List<Dict> dicts = dictService.queryDictByQueryCondition(Dict, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(dicts);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),dicts, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	
	
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(dictView);
		return mv;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(dictView);
		String dictId = request.getParameter("dictId");
		Dict dict = dictService.get(dictId);
		mv.addObject("dict", dict);
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
		String dictId = request.getParameter("dictId");
		String code = request.getParameter("dictCode");
		String name = request.getParameter("dictName");
				
		Dict dict = new Dict();
		String returnCode = "000";
		if(dictId!=null&&!"".equals(dictId)){
			dict = dictService.get(dictId);
			dict.setCode(code);
			dict.setName(name);	
			try{
				dictService.update(dict);
			}catch (Exception e) {
				//e.printStackTrace();
				returnCode = e.getMessage();
			}
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			dict.setCreated_time(format.format(new Date()));
			dict.setCode(code);
			dict.setName(name);
			try{
				dictService.save(dict);
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
	 * delete Dicts
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String dictIds = request.getParameter("dictIds");
		if(dictIds!=null&&!dictIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(dictIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator dictIterator =  aa.iterator();			
			while(dictIterator.hasNext()){
				String dictId = dictIterator.next().toString();
				dictService.remove(dictId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}
	
	
}