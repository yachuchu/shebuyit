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
import com.shebuyit.po.Product;
import com.shebuyit.po.Shop;
import com.shebuyit.po.Translation;
import com.shebuyit.service.ITranslationService;
import com.shebuyit.vo.DojoGridVO;

public class TranslationController extends BaseMultiActionController{	
	private ITranslationService translationService;
	
	private String translationSearchView;
	
	private String translationView;
	
	private String translationListView;
		
	public static String SEARCH_PARAM = "search_param";
	
	public static String SEARCH_PARAM_ZHNAME = "zhName";
	
	public static String SEARCH_PARAM_ENNAME = "enName";
	
	public static String SEARCH_PARAM_VALUE = "param_value";
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	public ITranslationService getTranslationService() {
		return translationService;
	}

	public void setTranslationService(ITranslationService translationService) {
		this.translationService = translationService;
	}
	
	public String getTranslationSearchView() {
		return translationSearchView;
	}

	public void setTranslationSearchView(String translationSearchView) {
		this.translationSearchView = translationSearchView;
	}

	public String getTranslationView() {
		return translationView;
	}

	public void setTranslationView(String translationView) {
		this.translationView = translationView;
	}
	

	public String getTranslationListView() {
		return translationListView;
	}

	public void setTranslationListView(String translationListView) {
		this.translationListView = translationListView;
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
		String searchParamStart = request.getParameter(ProductController.SEARCH_PARAM_START);
		String searchParamEnd = request.getParameter(ProductController.SEARCH_PARAM_END);
		
		Translation translation = new Translation();
		
		String defaultSortBy = "id";;
		
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParam != null){
			if (searchParam.equalsIgnoreCase(TranslationController.SEARCH_PARAM_ZHNAME)) {
				translation.setZhName(request.getParameter(TranslationController.SEARCH_PARAM_VALUE));		
				queryCondition.append("&"+TranslationController.SEARCH_PARAM+"="+TranslationController.SEARCH_PARAM_ZHNAME+"&"+TranslationController.SEARCH_PARAM_ZHNAME+"=" + request.getParameter(TranslationController.SEARCH_PARAM_VALUE));
			}else if(searchParam.equalsIgnoreCase(TranslationController.SEARCH_PARAM_ENNAME)) {
				translation.setEnName(request.getParameter(TranslationController.SEARCH_PARAM_VALUE));
				queryCondition.append("&"+TranslationController.SEARCH_PARAM+"="+TranslationController.SEARCH_PARAM_ENNAME+"&"+TranslationController.SEARCH_PARAM_ENNAME+"=" + request.getParameter(TranslationController.SEARCH_PARAM_VALUE));
			}
		}
		if (searchParamStart != null&&searchParamEnd != null){
			translation.setTime_start(request.getParameter(TranslationController.SEARCH_PARAM_START));
			translation.setTime_end(request.getParameter(TranslationController.SEARCH_PARAM_END));
			queryCondition.append("&"+TranslationController.SEARCH_PARAM_START+"=" + request.getParameter(TranslationController.SEARCH_PARAM_START)+"&"+TranslationController.SEARCH_PARAM_END+"=" + request.getParameter(TranslationController.SEARCH_PARAM_END));
		
		}
		if (searchParamStart != null&&searchParamEnd == null){
			translation.setTime_start(request.getParameter(TranslationController.SEARCH_PARAM_START));				
			queryCondition.append("&"+TranslationController.SEARCH_PARAM_START+"=" + request.getParameter(TranslationController.SEARCH_PARAM_START));
		
		}
		if (searchParamStart == null&&searchParamEnd != null){
			translation.setTime_end(request.getParameter(TranslationController.SEARCH_PARAM_END));				
			queryCondition.append("&"+TranslationController.SEARCH_PARAM_END+"=" + request.getParameter(TranslationController.SEARCH_PARAM_END));
		
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(translationSearchView);
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
			List<Translation> translations = translationService.queryTranslationByQueryCondition(translation, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(translations);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),translations, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	
	
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(translationView);
		return mv;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(translationView);
		String translationId = request.getParameter("translationId");
		Translation translation = translationService.get(translationId);
		mv.addObject("translation", translation);
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
		String translationId = request.getParameter("translationId");
		String zhName = request.getParameter("zhName");
		String enName = request.getParameter("enName");
				
		Translation translation = new Translation();
		String returnCode = "000";
		if(translationId!=null&&!"".equals(translationId)){
			translation = translationService.get(translationId);
			translation.setZhName(zhName);
			translation.setEnName(enName);	
			try{
				translationService.update(translation);
			}catch (Exception e) {
				//e.printStackTrace();
				returnCode = e.getMessage();
			}
		}else{
			Translation translationTemp = new Translation();
			translationTemp.setEnName(enName);
			List<Translation> translationList = translationService.queryTranslation(translationTemp);
			if(translationList!=null&&translationList.size()>0){
				returnCode = "001";
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				translation.setCreated_time(format.format(new Date()));
				translation.setZhName(zhName);
				translation.setEnName(enName);
				try{
					translationService.save(translation);
				}catch (Exception e) {
					//e.printStackTrace();
					returnCode = e.getMessage();
				}
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
		String translationIds = request.getParameter("translationIds");
		if(translationIds!=null&&!translationIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(translationIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator translationIterator =  aa.iterator();			
			while(translationIterator.hasNext()){
				String translationId = translationIterator.next().toString();
				translationService.remove(translationId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}
	
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(translationListView);
		Translation translation = new Translation();
		List<Translation> translationList = translationService.queryTranslation(translation);
		mv.addObject("translationList", translationList);
		return mv;
	}
	
	
}