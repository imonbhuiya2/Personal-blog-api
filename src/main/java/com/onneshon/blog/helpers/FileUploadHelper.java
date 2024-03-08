package com.onneshon.blog.helpers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadHelper {

	private String UPLOAD_DIR  ;
	
	public String uploadUserImage(MultipartFile image) {		
		this.UPLOAD_DIR = "src/main/resources/static/UserImages";		
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
