package com.onneshon.blog.payloads;

import java.util.List;

import com.onneshon.blog.entities.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDtoSecure {
	
	private int id;	
	
	@NotEmpty(message = "Name can not be empty")
	@Size(min =3, message = "Name should contain minimum 3 character")
	private String name;	

	private String image;
	
	@Size(max = 999, message = "Maximum 1000 characters allowed")
	private String about;
	
	private List<Role> roles;
	
}
