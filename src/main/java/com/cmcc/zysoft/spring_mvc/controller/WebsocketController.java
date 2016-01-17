package com.cmcc.zysoft.spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.zysoft.spring_mvc.websocket.WebsocketUtil;

@Controller
@RequestMapping("/websocket")
public class WebsocketController {
	
	@RequestMapping("/index.htm")
	public String index() {
		return "websockets";
	}
	
	@RequestMapping("/echo.htm")
	public String echo() {
		return "echo";
	}
	
	@RequestMapping("/broadcast/{content}")
	@ResponseBody
	public String broadcast(@PathVariable("content")String content) {
		WebsocketUtil.broadCast(content);
		return "已经发送请求至"+WebsocketUtil.size()+"个客户端";
	}
	
}
