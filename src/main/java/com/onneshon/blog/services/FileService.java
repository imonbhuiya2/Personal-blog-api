package com.onneshon.blog.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	//imageValidation
	Map<String, String> userImageValidation(MultipartFile file);
	
	//blog image validation
	Map<String, String> blogImageValidation(MultipartFile image);
	
	//uploading User image
	String uploadUserImage(MultipartFile file);
	//uploading blog image
	String uploadBlogImage(MultipartFile image);

		
}
