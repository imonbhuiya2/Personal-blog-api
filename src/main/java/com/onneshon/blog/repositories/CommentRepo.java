package com.onneshon.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onneshon.blog.entities.Blog;
import com.onneshon.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	List<Comment> findByBlog(Blog blog);
	
}
