package com.onneshon.blog.helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FileValidation {

	private Map<String, String> response = new HashMap<>();
	
	public Map<String, String> imageValidation(MultipartFile image) {
		
		int fileSizeinKb = (int) (image.getSize() / 1000);
		
//		System.out.println(fileSizeinKb);
//		System.out.println(image.getOriginalFilename());
//		System.out.println(image.getSize());
//		System.out.println(image.isEmpty());
//		System.out.println(image.getContentType());
//		System.out.println(image.getName());
		
		
		if(image.isEmpty()) {
			response.put("FileError", "Please, insert image!");
			return response;
		}
		
		if(fileSizeinKb < 100 || fileSizeinKb >5000 ) {
			response.put("FileError", "File size should between 100kb and 5000kb");
			return response;
		}
		
		if (!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg")) {
			response.put("FileError", "Only JPG, PNG format of file supports");
			return response;
		}		
	
		return response;
	}
	
}
