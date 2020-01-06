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
import com.gp.learners.service.PackageService;
import com.gp.learners.service.PdfService;
import com.gp.learners.service.PaperService;
import com.gp.learners.service.S3Service;
import com.gp.learners.service.UserService;
import com.gp.learners.service.VideoService;




@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class FileController {
	
	@Autowired
	S3Service s3Service;
	 	
	@Autowired
	UserService userService;
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	PdfService pdfService;
	
	@Autowired
	PaperService paperService;
	
	@Autowired
	VideoService videoService;
    
	/*
     * Download Files
     * type --> 1 = UserProfileImage
     * type --> 2 = PDF
     * type --> 3 = Paper
     * type --> 4 = Video
     */
	@GetMapping("/api/file/{userId}/{type}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("userId") Integer userId ,@PathVariable("type") Integer type) {
		
		System.out.println("File controller Viewing meth- TYpe"+type);
		ByteArrayOutputStream downloadInputStream = new ByteArrayOutputStream(); 
		System.out.println("type"+type);
		System.out.println("ID"+userId);
		if(type==1) {
			downloadInputStream = userService.downLoadProfileImage(userId);
		}else if(type==2) {
			System.out.println("File controller Viewing meth- PDF type 2");
			downloadInputStream = pdfService.downloadPdf(userId);
		}else if(type==3){
			System.out.println("File controller Viewing meth- Paper type 3");
			downloadInputStream = paperService.downloadPaper(userId);
		}else if(type==4){
			System.out.println("File controller Viewing meth- Video type 4");
			downloadInputStream = videoService.downloadVideo(userId);
		}
		
		if(downloadInputStream!=null) {
			String keyname ="";
			if(type==1) {
				 keyname=userId+".jpg";
				//keyname = userService.getFileKeyName(userId);
			}else if(type==2 ||type==3){
				//pdf service
				 keyname=userId+".pdf";
			}else if(type==4){
				//pdf service
				 keyname=userId+".mp4";
			}else {
				
			}
			  
			return ResponseEntity.ok()
					.contentType(contentType(keyname))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyname + "\"")
					.body(downloadInputStream.toByteArray());	
		}
		System.out.println("Null Stream");
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
	@PostMapping("file/upload/{id}/{type}")
    public ResponseEntity<Integer> uploadMultipartFile(@RequestParam("file") MultipartFile file ,@PathVariable("id") Integer id,@PathVariable("type") Integer type) {
		System.out.println("File upload Controller");
		try {
			Long fileSize = file.getSize();
			Long maxFileSize = 1L;
			
			if(type==1) {
				maxFileSize=9000000L;//9MB
			}else if(type==2||type==3){
				maxFileSize=9437184L;//9MB
			}else if(type==4){
				maxFileSize=20000000L;//20MB
			}else {//type=3
				maxFileSize=20000000L;//20MB
			}
			
			if(fileSize<=maxFileSize) {
				String reply=null;
				if(type==1) {
					reply = userService.uploadUserProfileImage(file, id);
				}else if(type==2){
					reply = pdfService.uploadPdf(file, id);
				}else if(type==3){
					reply = paperService.uploadPaper(file, id);
				}else if(type==4){
					reply = videoService.uploadVideo(file, id);
				}else {//type==3
					reply = packageService.uploadPackageImage(file, id);
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
