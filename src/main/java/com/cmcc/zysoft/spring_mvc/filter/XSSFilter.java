package com.cmcc.zysoft.spring_mvc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

/**
 * 防止SQL注入和XSS攻击的过滤器.
 * 
 * @author 李三来
 * @date 2013-01-10
 */
public class XSSFilter extends GenericFilterBean {

	/**
	 * 防止SQL注入和XSS攻击.
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param chain
	 *            FilterChain
	 * @throws IOException
	 *             异常
	 * @throws ServletException
	 *             异常
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request),
				response);
	}
}