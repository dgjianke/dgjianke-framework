package com.cmcc.zysoft.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("error")
public class ErrorController {
	
	/**
	 * 页面不存在.
	 */
	private static String PAGE_NOT_FOUND = "/error/404";
	
	/**
	 * 没有访问权限.
	 */
	private static String PAGE_NOT_GRANTED = "/error/403";
	
	/**
	 * 服务异常.
	 */
	private static String PAGE_EXCEPTION = "/error/500";
	
	@RequestMapping("handle404.htm")
	public String handle404() {
		return PAGE_NOT_FOUND;
	}
	
	@RequestMapping("handle403.htm")
	public String handle403() {
		return PAGE_NOT_GRANTED;
	}
	
	@RequestMapping("handle500.htm")
	public String handle500() {
		return PAGE_EXCEPTION;
	}
}
