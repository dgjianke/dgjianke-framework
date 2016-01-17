package com.cmcc.zysoft.spring_mvc.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.zysoft.spring_mvc.controller.BaseController;

@Controller
public class AuthController extends BaseController<AuthController>{
	
	@RequestMapping("callback")
	@ResponseBody
	public String callback(HttpServletRequest request) throws ServletRequestBindingException {
		String code = ServletRequestUtils.getStringParameter(request, "code");
		String state = ServletRequestUtils.getStringParameter(request, "state");
		logger.info("code:{},state:{}",code,state);
		return "code:"+code+":,state:"+state;
	}
}
