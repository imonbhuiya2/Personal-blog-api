package com.onneshon.blog.servicesImple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onneshon.blog.configs.AppConstants;
import com.onneshon.blog.entities.Role;
import com.onneshon.blog.entities.User;
import com.onneshon.blog.exceptions.ResourceNotFoundException;
import com.onneshon.blog.payloads.UserDto;
import com.onneshon.blog.payloads.UserDtoSecure;
import com.onneshon.blog.repositories.RoleRepo;
import com.onneshon.blog.repositories.UserRepo;
import com.onneshon.blog.services.UserServices;

@Service
public class UserServicesImple implements UserServices {	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	
	
	//REGISTER USER
	@Override
	public UserDtoSecure registerUser(UserDto userDto) {		
		User user = this.userDtoToUser(userDto);
		
		Role role = roleRepo.findById(AppConstants.ROLE_NORMAL).get();
		
		user.getRoles().add(role);		
		user.setPassword(passEncoder.encode(user.getPassword()));
		
		User addedUser= userRepo.save(user);
		
		return userToUserDtoSecure(addedUser);
	}
	
	
	
	//adding new User
	@Override
	public UserDto addUser(UserDto userDto) {
		User user = this.userDtoToUser(userDto);		
		User addedUser = userRepo.save(user);					
		return userToUserDto(addedUser);
	}

	
	
	//update user info
	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		User user = new User();
		user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserID ", userId));
			
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());		
		
		System.out.println(user.getPassword());
		System.out.println("================================");
		System.out.println("Working");
		System.out.println("================================");
		System.out.println(userDto.getPassword());
		User updatedUser = userRepo.save(user);
		
		return this.userToUserDto(updatedUser);
	}

	
	
	//deleting user
	@Override
	public void deleteUser(int userId) {
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserID ", userId));
		
		userRepo.delete(user);
		
	}

	
	//get user by id
	@Override
	public UserDto getUserById(int userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserID ", userId));		
		return this.userToUserDto(user);
	}
	
	
	//get all user
	@Override
	public List<UserDto> getAllUser() {
		
		List<User> userList =  userRepo.findAll();
		List<UserDto> users = new ArrayList<>();
		
		for(User user : userList){
			users.add(this.userToUserDto(user));
		}
		
		return users;
	}
	
	
	//get user by email
	public UserDto getUserByEmail(String email) {
		User findByEmail = null;
		
		try {
			findByEmail = userRepo.findByEmail(email).get();			
		}catch (Exception e) {
			if(findByEmail == null) {
				UserDto userDto = null;
				return userDto;
			}
		}	
		
		return this.userToUserDto(findByEmail);
	}
	
	
	
	
	
	
	
	
	
	
	
	//user to userDto 
	public UserDto userToUserDto(User user) {
		
		UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		userDto.setImage(user.getImage());	
		userDto.setRoles(user.getRoles());
		
		return userDto;
	}
	
	
	//user DTo to User
	public User userDtoToUser(UserDto userDto) {		
		User user = new User();
		
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setImage(userDto.getImage());
		user.setRoles(userDto.getRoles());
		
		return user;	
	}


	//user to user dto secure
	public UserDtoSecure userToUserDtoSecure(User user) {
		UserDtoSecure userDtoSecure = new UserDtoSecure();
		
		userDtoSecure.setId(user.getId());
		userDtoSecure.setName(user.getName());
		userDtoSecure.setAbout(user.getAbout());
		userDtoSecure.setImage(user.getImage());	
		userDtoSecure.setRoles(user.getRoles());
		
		return userDtoSecure;
	}

	//user to user dto secure
	public User userDtoSecureToUser(UserDtoSecure userDtoSecure) {
		
		User user = new User();	
		
		user.setId(userDtoSecure.getId());
		user.setName(userDtoSecure.getName());
		user.setAbout(userDtoSecure.getAbout());
		user.setImage(userDtoSecure.getImage());
		user.setRoles(userDtoSecure.getRoles());
		
		return user;
	}
	
	
	
	

}
