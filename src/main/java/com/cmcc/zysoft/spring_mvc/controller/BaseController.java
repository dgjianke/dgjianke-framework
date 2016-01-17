package com.cmcc.zysoft.spring_mvc.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseController<T> {
	
	private Class<T> clazz;
	
	protected Logger logger = LogManager.getLogger(clazz);
}
