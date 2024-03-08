package com.onneshon.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onneshon.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;	
	
	@NotEmpty(message = "Name can not be empty")
	@Size(min =3, message = "Name should contain minimum 3 character")
	private String name;
	
	@Size(min = 4, message = "Minimum 4 character required")
	private String password;
	
	//SET password a json ignore dile Password set kora jabe na
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}	
	@JsonProperty
	public void setPassword(String password) {
		 this.password = password;
	}
	
	
	@Email(message = "Invalid Email")
	private String email;

	private String image;
	
	@Size(max = 999, message = "Maximum 1000 characters allowed")
	private String about;

	private List<Role> roles =  new ArrayList<>();
	
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", image="
				+ image + ", about=" + about + "]";
	}
	
	
}
