package com.cmcc.zysoft.spring_mvc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cmcc.zysoft.spring_mvc.dao.UserDao;
import com.cmcc.zysoft.spring_mvc.model.DbUser;

public class CustomUserDetailsService implements UserDetailsService{
	
	protected static Logger logger = Logger.getLogger("service");  
	
	@Resource
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails user = null;
		try {
			DbUser dbUser = userDao.getDatabase(username);
			user = new User(dbUser.getUsername(), dbUser.getPassword(), getAuthorities(dbUser.getAccess()));
		} catch (Exception e) {
			logger.error("error in retrieving user");
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
	
	public Collection<GrantedAuthority> getAuthorities(Integer access) {
		List<GrantedAuthority> authList = new ArrayList<>();
		
		//所有用户默认拥有ROLE_USER权限
		logger.debug("Grant ROLE_USER to this user");
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		authList.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		
		//如果access为1，则拥有ROLE_ADMIN权限
		if(access == 1) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
			authList.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		}
		return authList;
	}

}
