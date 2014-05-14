package com.shebuyit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String currentURL = request.getRequestURI(); // 

		String targetURL = currentURL.substring(currentURL.indexOf("/", 1),currentURL.length()); // 
		HttpSession session = request.getSession(false);
		if (!(targetURL.startsWith("/login/"))) {
			// 
			if (session == null || session.getAttribute("user") == null) {
				// 
				System.out.println("request.getContextPath()="+ request.getContextPath());
				response.sendRedirect(request.getContextPath() + "/login/view.do");
				// 
				return;
			}
		}
		// 
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}
}