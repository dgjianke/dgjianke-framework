package com.cmcc.zysoft.spring.memcached;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;

@Repository("memUserDao")
public class UserDao {
	
	private static final Logger logger = LogManager.getLogger(UserDao.class);
	
	/**
	 * 命名空间，为user这个类专门设的一个，防止与其于的class但又相同的key的object冲突.
	 */
	private static final String NAMESPACE="user";
	
	private Map<String,User> users=new HashMap<String,User>();
	
	public void saveUser(User user) {
		updateUser(user);
	}
	
	/**	
	 * 当执行getById查询方法时，系统首先会从缓存中获取userId对应的实体
	 * 如果实体还没有被缓存，则执行查询方法并将查询结果放入缓存中
	 */
	@ReadThroughSingleCache(namespace = NAMESPACE, expiration = 3600)
	public User getById(@ParameterValueKeyProvider String userId) {
		logger.info("缓存中没有，从数据库读取：{}",userId);
		return users.get(userId);
	}
	
	/**
	 * 当执行updateUser方法时，系统会更新缓存中userId对应的实体
	 * 将实体内容更新成@*DataUpdateContent标签所描述的实体
	 */
	@UpdateSingleCache(namespace = NAMESPACE, expiration = 3600)
	public void updateUser(@ParameterValueKeyProvider @ParameterDataUpdateContent User user) {
		users.put(user.getId(), user);
	}
	
	/**
	 * 当执行deleteUser方法时，系统会删除缓存中userId对应的实体
	 */
	@InvalidateSingleCache(namespace = NAMESPACE)
	public void deleteUser(@ParameterValueKeyProvider String userId) {
		users.remove(userId);
	}
}
