package com.ustc.channel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ustc.channel.util.UploadComboUtil;

@RequestMapping("/pc/combo")
@Controller
public class ComboController {
	
	
	/**
	 * 跳转到上传页面.
	 * @return 
	 */
	@RequestMapping("/upload.htm")
	public String upload() {
		return "combo/upload";
	}
	
	
	/**
	 * 上传发展人套餐统计信息.
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadCombo.htm")
	@ResponseBody
	public Map<String, Object> uploadCombo(HttpServletRequest request) {
		Map<String, Object> map = UploadComboUtil.uploadCombo(request,"file");
		return map;
	}
	
	
}
