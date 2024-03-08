package com.onneshon.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onneshon.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
