package com.onneshon.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.onneshon.blog.entities.Role;
import com.onneshon.blog.repositories.RoleRepo;


@SpringBootApplication
public class OnneshonBlogApisApplication implements CommandLineRunner{

	@Autowired
//	private static PasswordEncoder myPasswordEncoder;
	private PasswordEncoder myPasswordEncoder;
	String x;
	
	public static void main(String[] args) {		
		SpringApplication.run(OnneshonBlogApisApplication.class, args);
		

	}

	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(myPasswordEncoder.encode("1234"));
		
		
		//ROLE ADD KORTESI
		Role roleNormal = new Role();
		roleNormal.setId(1001);
		roleNormal.setRole("ROLE_NORMAL");
		
		Role roleAdmin = new Role();
		roleAdmin.setId(1002);
		roleAdmin.setRole("ROLE_ADMIN");
		
		List<Role> roles = List.of(roleNormal, roleAdmin);
		
		roleRepo.saveAll(roles);
		System.out.println("Roles added");
	}
	
	
}

