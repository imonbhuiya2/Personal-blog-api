package com.onneshon.blog.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onneshon.blog.payloads.ApiResponse;
import com.onneshon.blog.payloads.UserDto;
import com.onneshon.blog.payloads.ValidationResponse;
import com.onneshon.blog.services.FileService;
import com.onneshon.blog.services.UserServices;
import com.onneshon.blog.servicesImple.FileServicesImple;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@RestController
@RequestMapping("/api")
public class UserControllers {

	@Autowired
	private UserServices userServices;
	
	@Autowired
	private ObjectMapper mapper;
	
//	@Autowired
//	private FileService fileService ;
	
	
	
	//adding user
	@PostMapping("/user/")
	public ResponseEntity<?> addUser(
			@RequestParam("userData") String userData,
			@RequestParam("image") MultipartFile file) {

		UserDto userDto = null;
		try {
			//STEP 1: converting the user Data into UserDto
			userDto = mapper.readValue(userData, UserDto.class);
		} catch (Exception e) {
			ApiResponse resp = new ApiResponse("InvalidDataConversion", false, "User Data Convert failed");
			return ResponseEntity.badRequest().body(resp);
		}
		
		//STEP 2: Validating User Data
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
		
		//checking if there is any error
		if(!violations.isEmpty()) {
			//@Autowire korle problem hoitasilo 
			ValidationResponse validResp = new ValidationResponse();
			Map<String, String> resp = validResp.getErrors(violations);
			return ResponseEntity.badRequest().body(resp);
		}
		
		
		
		//AUTO wire korle problem hocchilo. Ager value gula dhore rakhtesilo
		FileService fileService = new FileServicesImple();
		
		//STEP 3: file validation		
		//Map<String, String> imageViolation = new FileValidation().imageValidation(file);
		Map<String, String> imageViolation = fileService.userImageValidation(file);
		if(!imageViolation.isEmpty()) {
			return ResponseEntity.badRequest().body(imageViolation);
		}	
		
		//STEP 4: file upload		
		String imagePath = fileService.uploadUserImage(file);
		if(imagePath == null) {
			 return ResponseEntity.badRequest().body(new HashMap<>().put("FileError", "File Upload Fail"));
		}
		 
		 
		 //STEP 5: Upload Data
		 userDto.setImage(imagePath);
		 UserDto addedUser = userServices.addUser(userDto);		

		return new ResponseEntity<UserDto>(addedUser, HttpStatus.CREATED);
	}
	
	
	
	
	
	// Update New User Info
//	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
		UserDto updatedUser = userServices.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.CREATED);
	}

	// delete user by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
		userServices.deleteUser(userId);

		ApiResponse reponse = new ApiResponse("UserDelete", true, "User Deleted");

		return new ResponseEntity<ApiResponse>(reponse, HttpStatus.OK);
	}

	// get user by id
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable int userId) {
		UserDto user = userServices.getUserById(userId);
//		UserDto user = userServices.getUserByEmail("shubratodn44985@gmail.com");
		return ResponseEntity.ok(user);
	}

	// get all user 
	@GetMapping("/users/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> allUser = userServices.getAllUser();		
		return ResponseEntity.ok(allUser);
	}

}
