package com.onneshon.blog.servicesImple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onneshon.blog.entities.Category;
import com.onneshon.blog.exceptions.ResourceNotFoundException;
import com.onneshon.blog.payloads.CategoryDto;
import com.onneshon.blog.repositories.CategoryRepo;
import com.onneshon.blog.services.CategoryServices;

@Service
public class CategoryServiceImple implements CategoryServices{

	@Autowired
	CategoryRepo catRepo;
	
	
	//Adding Category
	@Override
	public CategoryDto addCategory(CategoryDto catDto) {
		Category cat = this.catDtoTocat(catDto); 
		Category addedCat = catRepo.save(cat);		
		return this.catTocatDto(addedCat);
	}

	
	
	//update category
	@Override
	public CategoryDto updateCategory(CategoryDto catDto, int id) {
		Category cat = catRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));			
		cat.setCategoryDescription(catDto.getCategoryDescription());
		cat.setCategoryTitle(catDto.getCategoryTitle());
		
		Category updatedCat = catRepo.save(cat);
		
		return this.catTocatDto(updatedCat);
	}

	
	//delete category
	@Override
	public void deleteCategory(int id) {		
		Category cat = catRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));		
		catRepo.delete(cat);		
	}

	
	//get category by id
	@Override
	public CategoryDto getCategoryById(int catId) {
		Category cat = catRepo.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", catId));				
		return this.catTocatDto(cat);
	}

	
	
	//get all categories
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categoriesList = catRepo.findAll();
		List<CategoryDto> categories = new ArrayList<>();
		
		for(Category category : categoriesList) {
			categories.add(this.catTocatDto(category));
		}		
		return categories;
	}

	
	
	
	
	//_______________________________________
	//DTO conversion
	//___________________________________
	public Category catDtoTocat(CategoryDto catDto) {
		Category cat = new Category();
		
		cat.setCategoryId(catDto.getCategoryId());
		cat.setCategoryDescription(catDto.getCategoryDescription());
		cat.setCategoryTitle(catDto.getCategoryTitle());
		
		return cat;
	}
	
	public CategoryDto catTocatDto(Category cat) {
		CategoryDto catDto = new CategoryDto();
		
		catDto.setCategoryId(cat.getCategoryId());
		catDto.setCategoryDescription(cat.getCategoryDescription());
		catDto.setCategoryTitle(cat.getCategoryTitle());
		
		return catDto;
	}
	
	
	
	
}
