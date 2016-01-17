package com.cmcc.zysoft.spring_mvc.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HandlerInterceptor1 extends HandlerInterceptorAdapter{
	
	private ThreadLocal<Long> beginTime = new ThreadLocal<Long>();
	
	private Logger logger = LogManager.getLogger(HandlerInterceptor1.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		beginTime.set(System.currentTimeMillis());
		logger.info("=========HandlerInterceptor1 preHandle========");
		//response.getWriter().write("HandlerInterceptor1 preHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("=========HandlerInterceptor1 postHandle========");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("=========HandlerInterceptor1 afterCompletion========");
		long end = System.currentTimeMillis();
		logger.info("consume:{}",end-beginTime.get());
	}
	
	
}
