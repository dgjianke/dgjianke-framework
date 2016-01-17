package com.cmcc.zysoft.spring_mvc.rest_template;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;


public class RestTemplateTest {
	
	Logger logger = LogManager.getLogger(RestTemplateTest.class);
	
	//@Test
	public void testGet() {
		RestTemplate restTemplate = new RestTemplate();
		String result=restTemplate.getForObject("http://192.168.215.37:8080/mobile/notice/getNoticeByChanneltwoId.htm?channeltwoId=e&page=1&rows=3", String.class );
		logger.info("get result:{}",result);
	}
	
	//@Test
	public void testGetMap() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> urlVariables  = new HashMap<String, Object>();
		urlVariables.put("channeltwoId", "e");
		urlVariables.put("page", "1");
		urlVariables.put("rows", "2");
		String result=restTemplate.getForObject("http://192.168.215.37:8080/mobile/notice/getNoticeByChanneltwoId.htm?channeltwoId={channeltwoId}&page={page}&rows={rows}", String.class ,urlVariables);
		logger.info("get result:{}",result);
	}
	
	//@Test
	public void testPost() {
		RestTemplate restTemplate = new RestTemplate();
		String result=restTemplate.postForObject("http://192.168.215.37:8080/mobile/notice/getNoticeByChanneltwoId.htm?channeltwoId=e&page=1&rows=3",null, String.class );
		logger.info("post result:{}",result);
	}
	
	//@Test
	public void testPostMap() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> urlVariables  = new HashMap<String, Object>();
		urlVariables.put("channeltwoId", "e");
		urlVariables.put("page", "1");
		urlVariables.put("rows", "2");
		String result=restTemplate.postForObject("http://58.16.129.243:9098/channel/mobile/notice/getNoticeByChanneltwoId.htm?channeltwoId={channeltwoId}&page={page}&rows={rows}",null, String.class ,urlVariables);
		logger.info("get result:{}",result);
	}
	
	@Test
	public void testPostMap2() {
		RestTemplate restTemplate = new RestTemplate();
		LinkedMultiValueMap<String, Object> urlVariables  = new LinkedMultiValueMap<String, Object>();
		urlVariables.add("channeltwoId", "e");
		urlVariables.add("page", "1");
		urlVariables.add("rows", "2");
		String result=restTemplate.postForObject("http://58.16.129.243:9098/channel/mobile/notice/getNoticeByChanneltwoId.htm",urlVariables, String.class);
		logger.info("get result:{}",result);
	}
	
}
