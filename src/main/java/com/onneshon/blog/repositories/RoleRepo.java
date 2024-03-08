package com.onneshon.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onneshon.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
