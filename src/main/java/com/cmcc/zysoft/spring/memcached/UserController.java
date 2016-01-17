package com.cmcc.zysoft.spring.memcached;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/memcache")
@Controller
public class UserController {
	
	@Resource
	private UserDao memUserDao; 
	
	@RequestMapping("/save")
	@ResponseBody
	public String saveUser(User user) {
		memUserDao.updateUser(user);
		return "success";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateUser(User user) {
		if(user.getId()==null||memUserDao.getById(user.getId())==null) {
			return "yi na na i";
		}
		memUserDao.updateUser(user);
		return "success";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteUser(User user) {
		if(user.getId()==null||memUserDao.getById(user.getId())==null) {
			return "yi na na i";
		}
		memUserDao.deleteUser(user.getId());
		return "success";
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public User getUser(String id) {
		return memUserDao.getById(id);
	}
}
