package com.cmcc.zysoft.spring_mvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmcc.zysoft.spring_mvc.model.User;

@Controller
@RequestMapping("/")
public class AnnotationHelloWorldController extends
		BaseController<AnnotationHelloWorldController> {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/hello")
	public ModelAndView hello() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		mv.addObject("message", "hello annotation world");
		return mv;
	}

	@RequestMapping("/role")
	public ModelAndView role(@RequestParam(value = "role") String[] role) {
		logger.info(Arrays.toString(role));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		mv.addObject("message", Arrays.toString(role));
		return mv;
	}

	@RequestMapping("/model")
	public ModelAndView model(Model model) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		model.addAttribute("message", "hello annotation world");
		return mv;
	}

	@RequestMapping("/users/{userId}/topics/{topicId}")
	public ModelAndView topic(Model model,
			@PathVariable(value = "userId") String userId,
			@PathVariable(value = "topicId") String topicId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		model.addAttribute("message", userId + "," + topicId);
		return mv;
	}

	@RequestMapping("/cookie")
	public ModelAndView cookie(Model model,
			@CookieValue(value = "JSESSIONID") String sessionId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		model.addAttribute("message", sessionId);
		return mv;
	}
	
	@RequestMapping("/header")
	public ModelAndView header(Model model,
			@RequestHeader("User-Agent") String userAgent,@RequestHeader(value="Host") String host) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		model.addAttribute("message", userAgent+"<br/>"+host);
		return mv;
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public Map<String, Object> json() {
		User user = new User();  
        user.setUsername("肖雨");  
        user.setPassword("123");  
        user.setAge(123);  
        user.setSex("male");  
        user.setBirthday(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("json", "hello world");
		map.put("time", new Date());
		map.put("user", "dgjianke");
		return map;
	}
	
	@RequestMapping("/string")
	@ResponseBody
	public String string() {
		String result = "000飞狐";
		return result;
	}
	
	@RequestMapping("/getUser")
	public User getUser(Model model) throws UnsupportedEncodingException {
		User user = new User();
		String name = "肖雨";
        user.setUsername(new String(name.getBytes("utf-8"),"utf-8"));  
        user.setPassword("123");  
        user.setAge(123);  
        user.setSex("male");  
        user.setBirthday(new Date());
        model.addAttribute("fileName", user.getUsername());
        return user;  
	}
	
	@RequestMapping("/getUser2")
	public ModelAndView getUser2(Model model) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView("dgExcel");
		User user = new User();
		String name = "肖雨";
        user.setUsername(new String(name.getBytes("utf-8"),"utf-8"));  
        user.setPassword("123");  
        user.setAge(123);  
        user.setSex("male");  
        user.setBirthday(new Date());
        model.addAttribute("fileName", user.getUsername());
        model.addAttribute("user", user);
        return mv;  
	}
	
	@RequestMapping("/getUser3")
	public ModelAndView getUser3(Model model) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView("dgPdf");
		User user = new User();
		String name = "肖雨";
        user.setUsername(new String(name.getBytes("utf-8"),"utf-8"));  
        user.setPassword("123");  
        user.setAge(123);  
        user.setSex("male");  
        user.setBirthday(new Date());
        model.addAttribute("fileName", user.getUsername());
        model.addAttribute("user", user);
        return mv;  
	}
	
	
	@RequestMapping("jsnp.htm")
	@ResponseBody
	public String jsonpTest(String echo,String callback) {
		echo = callback+"({\"msg\":\""+echo+"\"})";
		return echo;
	}
	
	@RequestMapping("jsnp2.htm")
	@ResponseBody
	public String jsonpTest2(String echo,String callback) {
	    int count = Integer.parseInt(echo);
	    echo = callback+"({\"msg\":\""+String.valueOf(count)+"\"})";
		return echo;
	}
	
	
	
}
