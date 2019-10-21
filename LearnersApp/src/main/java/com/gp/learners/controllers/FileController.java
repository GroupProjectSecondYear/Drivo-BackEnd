package com.gp.learners.controllers;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.simpleworkflow.flow.core.TryCatch;
import com.gp.learners.service.S3Service;
import com.gp.learners.service.UserService;



@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class FileController {
	
	@Autowired
	S3Service s3Service;
	 	
	@Autowired
	UserService userService;
    
	/*
     * Download Files
     * type --> 1 = UserProfileImage
     * type --> 2 = PDF
     */
	@GetMapping("/api/file/{userId}/{type}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("userId") Integer userId ,@PathVariable("type") Integer type) {
		
		ByteArrayOutputStream downloadInputStream = new ByteArrayOutputStream(); 
		if(type==1) {
			downloadInputStream = userService.downLoadProfileImage(userId);
		}else {
			//pdf service
		}
		
		if(downloadInputStream!=null) {
			String keyname ="";
			if(type==1) {
				keyname = userService.getFileKeyName(userId);
			}else {
				//pdf service
			}
			  
			return ResponseEntity.ok()
					.contentType(contentType(keyname))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyname + "\"")
					.body(downloadInputStream.toByteArray());	
		}
		return ResponseEntity.notFound().build();
	}
	
	/*
	 * List ALL Files
	 */
//	@GetMapping("/api/file/all")
//	public List<String> listAllFiles(){
//		return s3Service.listFiles();
//	}
	
	private MediaType contentType(String keyname) {
		String[] arr = keyname.split("\\.");
		String type = arr[arr.length-1];
		switch(type) {
			case "txt": return MediaType.TEXT_PLAIN;
			case "png": return MediaType.IMAGE_PNG;
			case "jpg": return MediaType.IMAGE_JPEG;
			default: return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
	
	/*
	 * Upload file
	 * type --> 1 = UserProfileImage
     * type --> 2 = PDF
	 */
	@PostMapping("file/upload/{userId}/{type}")
    public ResponseEntity<Integer> uploadMultipartFile(@RequestParam("file") MultipartFile file ,@PathVariable("userId") Integer userId,@PathVariable("type") Integer type) {
		try {
			Long fileSize = file.getSize();
			Long maxFileSize = 1L;
			
			if(type==1) {
				maxFileSize=9437184L;//9MB
			}else {
				//pdf max file size
			}
			
			if(fileSize<=maxFileSize) {
				String reply=null;
				if(type==1) {
					reply = userService.uploadUserProfileImage(file, userId);
				}else {
					//pdf Service
				}
				
				if(reply!=null) {
					return ResponseEntity.ok(1);
				}
			}else {
				return ResponseEntity.ok(0);
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}	
		return ResponseEntity.notFound().build();
    } 
}
