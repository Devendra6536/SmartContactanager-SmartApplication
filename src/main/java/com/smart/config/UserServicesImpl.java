package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

public class UserServicesImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetching user from the database using dao repository
		User user = userRepository.getUserByUserName(username);
		
		if(user==null) {
			throw new  UsernameNotFoundException("user not found");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		System.out.println(user );
		return customUserDetails;
	}

}
