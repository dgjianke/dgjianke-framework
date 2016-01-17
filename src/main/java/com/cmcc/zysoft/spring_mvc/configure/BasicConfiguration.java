package com.cmcc.zysoft.spring_mvc.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:META-INF/spring/*-context.xml"})
public class BasicConfiguration {
	
}
