package com.cmcc.zysoft.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/redirect")
@Controller
public class RedirectController {
	
	@RequestMapping("/r.htm")
	public String redirect() {
		return "redirect";
	}
}
