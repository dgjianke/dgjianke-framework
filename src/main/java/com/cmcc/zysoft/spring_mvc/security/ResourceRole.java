package com.cmcc.zysoft.spring_mvc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ResourceRole {
	
	public static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> generate() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> map = new LinkedHashMap<>();
		RequestMatcher matcher1 = new AntPathRequestMatcher("/auth/login");
		ConfigAttribute configAttribute1 = new SecurityConfig("ROLE_ANONYMOUS");
		ArrayList<ConfigAttribute> list1 = new ArrayList<>(1);
		list1.add(configAttribute1);
		RequestMatcher matcher2 = new AntPathRequestMatcher("/main/admin");
		ConfigAttribute configAttribute2 = new SecurityConfig("ROLE_ADMIN");
		ArrayList<ConfigAttribute> list2 = new ArrayList<>(1);
		list2.add(configAttribute2);
		RequestMatcher matcher3 = new AntPathRequestMatcher("/main/common");
		ConfigAttribute configAttribute3 = new SecurityConfig("ROLE_USER");
		ArrayList<ConfigAttribute> list3 = new ArrayList<>(1);
		list3.add(configAttribute3);
		map.put(matcher1,list1);
		map.put(matcher2,list2);
		map.put(matcher3,list3);
		return map;
	}
}
