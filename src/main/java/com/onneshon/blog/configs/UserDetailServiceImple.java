package com.onneshon.blog.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onneshon.blog.entities.User;
import com.onneshon.blog.exceptions.ResourceNotFoundException;
import com.onneshon.blog.repositories.UserRepo;

@Service
public class UserDetailServiceImple implements UserDetailsService{

	
	@Autowired
	private UserRepo userRepo;
	
	
	//step 2 (security):
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("UserName", "userName",username ));
		
		//step 3 UserDetails er object pass kora
		CustomUserDetails userDetails = new CustomUserDetails(user);
		
		return userDetails;
	}

}
