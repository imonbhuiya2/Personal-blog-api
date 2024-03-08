package com.onneshon.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

	private String fieldInfo;
	private boolean  isSuccess;
	private String fieldMsg;
	
	
}
