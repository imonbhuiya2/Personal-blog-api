package com.onneshon.blog.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private int categoryId;
	
	@NotNull(message = "Description can not be null")
	private String categoryDescription;
	
	@NotNull(message = " Title can not be null")
	@Size(min = 3, message = "Minimum 3 character")
	private String categoryTitle;
	
}
