package com.cmcc.zysoft.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/nginx")
@Controller
public class NginxController {
	
	@RequestMapping("/{sub}/{file}")
	public String nginx(@PathVariable("sub") String sub,@PathVariable("file") String file) {
		return "nginx/nginx"+sub+"/"+file;
	}
}
