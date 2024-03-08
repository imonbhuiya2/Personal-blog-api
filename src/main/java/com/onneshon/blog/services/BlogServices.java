package com.onneshon.blog.services;

import com.onneshon.blog.payloads.BlogDto;
import com.onneshon.blog.payloads.PageResponse;

public interface BlogServices {

	//create Blog
	BlogDto addBlog(BlogDto blog, int userId);
	
	//update Blog
	BlogDto updateBlog(BlogDto blog, int blogId);
	
	//delete blog
	void deleteBlog(int blogId);
	
	//get blog by id
	BlogDto getBlogById(int blogId);
	
	//get all blogs
	PageResponse getAllBlogs(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	//get all blogs by User
	PageResponse getAllBlogsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	//get all Blogs by category id
	PageResponse getAllBlogsByCategory(int catId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	//search blog
	PageResponse searchBlogs(String search_query, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
}

