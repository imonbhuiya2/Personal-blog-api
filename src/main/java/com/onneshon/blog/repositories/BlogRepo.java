package com.onneshon.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.onneshon.blog.entities.Blog;
import com.onneshon.blog.entities.Category;
import com.onneshon.blog.entities.User;

public interface BlogRepo extends JpaRepository<Blog, Integer>{
	
	List<Blog> findByUser(User user);
	
	List<Blog> findByCategory(Category category);
	
	//pageable er jonno
	Page<Blog> findByUser(User user, Pageable page);
	
	Page<Blog> findByCategory(Category category,  Pageable page);
	
	Page<Blog> findByBlogTitleContaining(String search_query, Pageable page);
	
	
}
