package com.cmcc.zysoft.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.zysoft.spring_mvc.model.DbUser;

@Controller
public class FormController extends BaseController<FormController>{
	
	@RequestMapping("/formSubmit")
	@ResponseBody
	public String form(DbUser user) {
		logger.info(user.getUsername());
		return "SUCCESS";
	}
	
	@RequestMapping("/formPage")
	public String formPage() {
		return "form";
	}
	
}
