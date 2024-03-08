package com.onneshon.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageResponse {
	
	private List<BlogDto> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private long totalElements;
	
	private int totalPages;
	
	private int numberOfElements;
	
	private boolean isEmpty;
	
	private boolean isFirst;
	
	private boolean isLast;
	
	
}
