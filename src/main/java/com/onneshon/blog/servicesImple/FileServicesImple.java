package com.onneshon.blog.servicesImple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.onneshon.blog.configs.AppConstants;
import com.onneshon.blog.services.FileService;

@Service
public class FileServicesImple implements FileService{

	private String UPLOAD_DIR ;
	
	private int minImageSize, maxImageSize;	
	
	private Map<String, String> response = new HashMap<>();	
	
	
	//USER IMAGE VALIDATION
	@Override
	public Map<String, String> userImageValidation(MultipartFile image) {
		
		this.minImageSize = AppConstants.MINIMUM_SIZE_OF_USERIMAGE_KB;
		this.maxImageSize = AppConstants.MAXIMUM_SIZE_OF_USERIMAGE_KB;
		
		response = imageValidation(image);		
		return response;
	}
	
	
	//USER IMAGE VALIDATION
	@Override
	public Map<String, String> blogImageValidation(MultipartFile image) {
		
		this.minImageSize = AppConstants.MINIMUM_SIZE_OF_USERIMAGE_KB;
		this.maxImageSize = AppConstants.MAXIMUM_SIZE_OF_BLOGIMAGE_KB;
		
		response = imageValidation(image);		
		return response;
	}
	
	
	//image validation
	private Map<String, String> imageValidation(MultipartFile image) {

		int fileSizeinKb = (int) (image.getSize() / 1000);

		if (image.isEmpty()) {
			response.put("FileError", "Please, insert image!");
			return response;
		}
		
		if (!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg")) {
			response.put("FileError", "Only JPG, PNG format of file supports");
			return response;
		}

		if (fileSizeinKb < minImageSize || fileSizeinKb > maxImageSize) {
			response.put("FileError", "File size should between "+minImageSize+"kb and "+maxImageSize+"kb"+fileSizeinKb);
			return response;
		}		

		return response;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	//upload User Image
	public String uploadUserImage(MultipartFile image) {		
		this.UPLOAD_DIR = "src/main/resources/static/UserImages";		
		return uploadFile(image);
	}
	
	@Override
	//Blog User Image
	public String uploadBlogImage(MultipartFile image) {		
		this.UPLOAD_DIR = "src/main/resources/static/BlogImages";		
		return uploadFile(image);
	}
	
	
	
	
	
	
	//uploading file
	private String uploadFile(MultipartFile image) {
		
		//Random text generate
		SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[10];
        random.nextBytes(randomBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : randomBytes) {
            sb.append(String.format("%02x", b));
        }
        String randomHexCode = sb.toString();
        
        //path directory
        String filePath ="User_Image_"+randomHexCode+"_"+System.currentTimeMillis()+"_.jpg";		
		
		try {			
			//making the directory
			File f = new File(UPLOAD_DIR);
			f.mkdir();
			
			InputStream inputStream = image.getInputStream();
			byte data[] = new byte[inputStream.available()];
			inputStream.read(data);
			
			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+File.separator+filePath);
			fos.write(data);
			
			fos.flush();
			fos.close();
			
			return filePath;
			
		}catch (Exception e) {
			System.out.println("FILE UPLOAD FAIL"+e);
			return null;
		}
	}

	
	
	

}
