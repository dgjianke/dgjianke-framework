package com.cmcc.zysoft.spring_mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class TestInterceptorController extends BaseController<TestInterceptorController> implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("=======TestInterceptor handleRequest======");
		ModelAndView mv = new ModelAndView("test");
		return mv;
	}

}
