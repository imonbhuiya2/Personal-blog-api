package com.onneshon.blog.services;

import java.util.List;

import com.onneshon.blog.payloads.CategoryDto;


public interface CategoryServices {

	
	//add category
	CategoryDto addCategory(CategoryDto catDto);
	
	//update category
	CategoryDto updateCategory(CategoryDto catDto, int id);
	
	//delete category
	void deleteCategory(int id);
	
	//get category
	CategoryDto getCategoryById(int catId);
	
	//get all category
	List<CategoryDto> getAllCategory();
	
	
}
