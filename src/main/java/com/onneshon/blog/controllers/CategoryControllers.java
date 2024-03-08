package com.onneshon.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onneshon.blog.payloads.ApiResponse;
import com.onneshon.blog.payloads.CategoryDto;
import com.onneshon.blog.services.CategoryServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryControllers {

	@Autowired
	CategoryServices catServices;
	
	//creating category
	@PostMapping("/")
	public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDto catDto){
		CategoryDto addCategory = catServices.addCategory(catDto);
		return new ResponseEntity<>(addCategory, HttpStatus.CREATED);
	}
	
	//update category
	@PutMapping("/{catId}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable("catId") int catId){
		CategoryDto updateCategory = catServices.updateCategory(catDto, catId);
		return new ResponseEntity<>(updateCategory, HttpStatus.CREATED);
	}
	
	
	//Delete category
	@DeleteMapping("/{catId}")
	public ResponseEntity<?> updateCategory(@PathVariable("catId") int catId){
		catServices.deleteCategory(catId);
		ApiResponse response = new ApiResponse("DeleteCategory", true, "Category Deleted");
		return ResponseEntity.ok(response);
	}
	
	
	//get category
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId") int catId){
		CategoryDto category = catServices.getCategoryById(catId);
		return ResponseEntity.ok(category);
	}
	

	//get All categories
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> allCategory = catServices.getAllCategory();
		return ResponseEntity.ok(allCategory);
	}
	
	
}
