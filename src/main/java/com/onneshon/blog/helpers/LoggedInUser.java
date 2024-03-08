package com.onneshon.blog.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.onneshon.blog.entities.User;
import com.onneshon.blog.payloads.UserDto;
import com.onneshon.blog.servicesImple.UserServicesImple;



public class LoggedInUser {	
	
	public User getUser(){		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//		UserServicesImple userService = new UserServicesImple();
//		UserDto user = userService.getUserByEmail(authentication.getName());
//		
//		if(user != null) {
//			System.out.println("=======================================");
//			System.out.println("User is logged in" + user.getEmail());
//			System.out.println("=======================================");
//		}else {
//			System.out.println("=======================================");
//			System.out.println("User is paichi na" + user.getEmail());
//			System.out.println("=======================================");
//		}
//		
//		
//		if(authentication != null && authentication.isAuthenticated()) {			
//			 userService = new UserServicesImple();
//			 user = userService.getUserByEmail(authentication.getName());
//			
//			if(user != null) {
//				System.out.println("=======================================");
//				System.out.println("User is logged in" + user.getEmail());
//				System.out.println("=======================================");
//			}
//		}else{
//			System.out.println("=======================================");
//
//			System.out.println("User is NOT LOGGED IN");
//			System.out.println("=======================================");
//		};
//
//		
		return null;
	}
	
}
