package com.onneshon.blog.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onneshon.blog.configs.UserDetailServiceImple;
import com.onneshon.blog.configs.jwt.JwtUtil;
import com.onneshon.blog.payloads.ApiResponse;
import com.onneshon.blog.payloads.JwtAuthResponse;
import com.onneshon.blog.payloads.JwtLoginRequest;
import com.onneshon.blog.payloads.UserDto;
import com.onneshon.blog.payloads.UserDtoSecure;
import com.onneshon.blog.payloads.ValidationResponse;
import com.onneshon.blog.services.FileService;
import com.onneshon.blog.services.UserServices;
import com.onneshon.blog.servicesImple.FileServicesImple;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailServiceImple userDetailServiceImple;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private UserServices userServices;
	

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody JwtLoginRequest userInfo) throws Exception{
	
		String userName = userInfo.getUserName();
		String password = userInfo.getPassword();			
		//authentication kortesi j User er password thik ache ki na
		this.authenticate(userName, password);		
		//sob thik ache ekhane
		UserDetails userDetails = userDetailServiceImple.loadUserByUsername(userName);
		String token = jwtUtil.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);		
		response.setUser(userServices.getUserByEmail(userName));
		
		return ResponseEntity.ok(response);		
	}
	
	
	
	//register user
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(
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
		
		
		//checking if user is already exist or not		
		UserDto userByEmail = userServices.getUserByEmail(userDto.getEmail());
		if(userByEmail != null) {			
			Map<String, String> resp = new HashMap<>();
			resp.put("message", "Email already Exist!");
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
		 UserDtoSecure addedUser = userServices.registerUser(userDto);	 
		 	
		 
		return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
	}
	
	
	
	
	
	
	
	
	
	
	//User name r password valid ki na check kore;
	private void authenticate(String userName, String password) throws Exception {		
		try {			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		}catch (DisabledException e) {
			throw new Exception("User Disabled", e);
		}catch (BadCredentialsException e) {
			throw new Exception("Bad Credential", e);
		}catch (InternalAuthenticationServiceException e) {
			throw new UsernameNotFoundException("Invalid UserName!", e);
			//throw new BadCredentialsException("Username or Password Invalid!", e);
		}
	}
	
}
