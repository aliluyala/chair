package com.chair.manager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chair.manager.service.ManagerService;

public class UserInterceptor implements HandlerInterceptor {

	
	private static Logger LOGGER = Logger.getLogger(UserInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(request.getSession().getAttribute("user")!=null){
			return true;
		}
		String url="/"+request.getRequestURI().split("/")[1]+"/login.jsp";
		LOGGER.debug("登录失败超时\t跳转页面url="+url);
		response.sendRedirect(url);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug(request.getParameter("user")+"登录失败----postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		LOGGER.debug(request.getParameter("user")+"登录失败----afterCompletion");
	}

}
