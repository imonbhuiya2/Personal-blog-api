package com.onneshon.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.onneshon.blog.payloads.ApiResponse;
import com.onneshon.blog.payloads.BlogDto;
import com.onneshon.blog.payloads.CommentDto;
import com.onneshon.blog.payloads.UserDto;
import com.onneshon.blog.services.CommentServices;
import com.onneshon.blog.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentControllers {

	@Autowired
	private CommentServices commentServices;
	
	@Autowired
	private UserServices userServices;
	
	@PostMapping("/blog/{blogId}/comment")
	public ResponseEntity<?> addComment(
//			@Valid @RequestBody CommentDto comment
			@RequestParam("content") String content,
			@PathVariable int blogId			
			){
		
		CommentDto comment = new CommentDto();
		comment.setContent(content);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		System.out.println(authentication.getName());
		UserDto user = userServices.getUserByEmail(authentication.getName());		
		comment.setUser(user);		
		
		CommentDto addedComment = commentServices.addComment(comment, blogId);		
		return ResponseEntity.ok(addedComment);
	}
	
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int commentId){
		commentServices.deleleComment(commentId);
		ApiResponse response  = new ApiResponse("CommentDeletation", true, "Comment Deleted");
		
		return ResponseEntity.ok(response);
		
	}
	
	
	@GetMapping("/comment/blog/{blogId}")
	public ResponseEntity<?> getCommentsByBlog(@PathVariable int blogId){
		BlogDto blogDto = new BlogDto();
		blogDto.setId(blogId);
		
		List<CommentDto> commentsForBlog = commentServices.getCommentsForBlog(blogDto);
		
		return ResponseEntity.ok(commentsForBlog);
	}
	
	
}
