package com.shebuyit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.shebuyit.po.Dict;
import com.shebuyit.po.User;
import com.shebuyit.service.IUserService;

public class LoginController extends BaseMultiActionController{	
	
	private IUserService userService;
	
	private String loginView;
	

	public String getLoginView() {
		return loginView;
	}

	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}
	
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 *  The login view page.
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView(loginView);
		return mv;

	}
	
	
	
	/**
	 *  The login .
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		ModelAndView mv = new ModelAndView(loginView);		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setUserName(userName);
		List<User> users = userService.queryUser(user);
		String returnCode = "000";
		if(users==null||users.size()>0){			
			User loginUser = users.get(0);
			if(loginUser.getPassword().equals(password)){
				request.getSession().setAttribute("user", loginUser);
			}else{
				returnCode="001";	
			}
			
		}else{
			returnCode="002";
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
	 *  The login .
	 * 
	 * @param request
	 * @param response
	 * @param dojoGridVO
	 * @return
	 */
	public ModelAndView changeLanguage(HttpServletRequest request, HttpServletResponse response)throws IOException {	
		HttpSession session = request.getSession();	        
        String language = request.getParameter("language");
        if ("Chinese".equals(language)) {
            session.setAttribute("GLOBAL_LANGUAGE", "zh");
        }
        if ("English".equals(language)) 
        {
            session.setAttribute("GLOBAL_LANGUAGE", "en");
        }
        response.getWriter().print("000");    
	    return null;
    }
	

	/**
	 * logOut.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {	
		request.getSession().invalidate(); 
		return new ModelAndView("redirect:/login/view.do"); 
	}
	
}