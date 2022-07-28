package com.greatlearning.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.greatlearning.employee.dao.UserRepository;
import com.greatlearning.employee.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database
		
		User user = userRepository.getUserByName(username);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("Could not find the user !!");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}
	

}
