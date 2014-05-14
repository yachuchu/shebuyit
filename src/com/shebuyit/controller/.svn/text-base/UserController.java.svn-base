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
import com.shebuyit.po.User;
import com.shebuyit.service.IUserService;
import com.shebuyit.vo.DojoGridVO;

public class UserController extends BaseMultiActionController{		
	
	private String userSearchView;
	
	private String userView;
	
	private String userInfoView;
	
	private IUserService userService;
	
	public static String SEARCH_PARAM_USERNAME = "userName";
	
	public static String SEARCH_PARAM_TYPE = "type";
	
	public static String SEARCH_PARAM_START = "param_start";
	
	public static String SEARCH_PARAM_END = "param_end";
	
	public String getUserSearchView() {
		return userSearchView;
	}

	public void setUserSearchView(String userSearchView) {
		this.userSearchView = userSearchView;
	}

	public String getUserView() {
		return userView;
	}

	public void setUserView(String userView) {
		this.userView = userView;
	}

	public String getUserInfoView() {
		return userInfoView;
	}

	public void setUserInfoView(String userInfoView) {
		this.userInfoView = userInfoView;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
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
			dojoGridVO.getPaging().setSize(20);
			dojoGridVO.getPaging().setCurrent(currentPageNum);
		}
		String searchParamUserName = request.getParameter(UserController.SEARCH_PARAM_USERNAME);
		String searchParamType = request.getParameter(UserController.SEARCH_PARAM_TYPE);
		User user = new User();
		
		String defaultSortBy = "id";;
		
		StringBuilder queryCondition = new StringBuilder();
		queryCondition.append("?1=1");
		if (searchParamUserName != null){
			user.setUserName(request.getParameter(UserController.SEARCH_PARAM_USERNAME));				
			queryCondition.append("&"+UserController.SEARCH_PARAM_USERNAME+"=" + request.getParameter(UserController.SEARCH_PARAM_USERNAME));
		
		}
		if (searchParamType != null){
			user.setType(request.getParameter(UserController.SEARCH_PARAM_TYPE));				
			queryCondition.append("&"+UserController.SEARCH_PARAM_TYPE+"=" + request.getParameter(UserController.SEARCH_PARAM_TYPE));
		
		}
		dojoGridVO.getPaging().setQueryCondition(queryCondition.toString());
		ModelAndView mv = new ModelAndView(userSearchView);
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
			List<User> users = userService.queryUserByQueryCondition(user, sortby, isAsc, dojoGridVO.getPaging());
			if (doSearch != null && doSearch.trim().length() != 0) {
				dojoGridVO.setData(users);
				returnAjaxResponse(request, response, dojoGridVO, null);
				return null;
			}
			String jsonParcelVoList = ObjectToJson(request.getLocale(),users, null);
			dojoGridVO.setJsonData(jsonParcelVoList);
			mv.addObject(DOJO_GRID_VO_REF, dojoGridVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}	

	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(userView);
		return mv;
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(userView);
		String userId = request.getParameter("userId");
		User user = userService.get(userId);
		mv.addObject("myuser", user);
		return mv;
	}
	
	public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) {
		ModelAndView mv = new ModelAndView(userInfoView);
		User seeionUser = (User) request.getSession().getAttribute("user");
//		String userId = seeionUser.getId().toString();
//		User user = userService.get(userId);
		mv.addObject("userInfo", seeionUser);
		return mv;
	}
	
	public ModelAndView saveInfo(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws ServletException, IOException {	
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		User user = userService.get(userId);
		user.setPassword(password);
		String returnCode = "000";
		try{
			userService.update(user);
		}catch (Exception e) {
			//e.printStackTrace();
			returnCode = e.getMessage();
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
	 * It is a dummy code, need to be replaced.
	 * The create method will be called in ajax way.
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) throws ServletException, IOException {			
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
				
		User user = new User();
		String returnCode = "000";
		if(userId!=null&&!"".equals(userId)){
			user = userService.get(userId);
			if(type!=null){
				user.setType(type);
			}
			user.setPassword(password);
			try{
				userService.update(user);
			}catch (Exception e) {
				//e.printStackTrace();
				returnCode = e.getMessage();
			}
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			user.setCreated_time(format.format(new Date()));
			user.setUserName(userName);
			user.setType(type);
			user.setPassword(password);
			try{
				userService.save(user);
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
	 * delete users
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO)  throws IllegalStateException, ServletException, IOException {				
		String userIds = request.getParameter("userIds");
		if(userIds!=null&&!userIds.equals("")){	
			JSONArray aa = null;
			try {
				aa = JSONArray.parse(userIds);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
			Iterator userIterator =  aa.iterator();			
			while(userIterator.hasNext()){
				String userId = userIterator.next().toString();
				userService.remove(userId);
			}
		}
		String jsonStr = "{returnCode:000}";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonStr);
		return null; 

	}


	
}
