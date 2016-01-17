package com.cmcc.zysoft.spring.memcached;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1353233637428701965L;

	private String id;
	
	private String username;
	
	private String password;
	
	/**
	 * 指定@CacheKeyMethod的目的在于存储这个实体到memcached中时，key值便为id，如果不指定则按object.toString()处理;
	 * @return
	 */
	@CacheKeyMethod
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
