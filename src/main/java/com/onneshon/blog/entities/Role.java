package com.onneshon.blog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
	
	@Id	
	private int id;
	
	@Column(name = "role",length = 20, nullable = false)	
	private String role;
	
//	@ManyToOne
//	private User user;
	
}
