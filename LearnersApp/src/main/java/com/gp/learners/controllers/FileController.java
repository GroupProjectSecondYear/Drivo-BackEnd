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
     */
	@GetMapping("/api/file/{userId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("userId") Integer userId) {
		ByteArrayOutputStream downloadInputStream = userService.downLoadProfileImage(userId);
		
		if(downloadInputStream!=null) {
			String keyname = userService.getFileKeyName(userId);
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
	@GetMapping("/api/file/all")
	public List<String> listAllFiles(){
		return s3Service.listFiles();
	}
	
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
	 */
	@PostMapping("file/upload/{userId}")
    public ResponseEntity<Integer> uploadMultipartFile(@RequestParam("file") MultipartFile file ,@PathVariable("userId") Integer userId) {
		try {
			Long fileSize = file.getSize();
			if(fileSize<=9437184L) {
				String reply = userService.uploadUserProfileImage(file, userId);
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
