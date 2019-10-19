package com.gp.learners.service;



import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.config.security.JwtInMemoryUserDetailsService;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	S3Service s3Service;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	JwtInMemoryUserDetailsService jwtInMemoryUserDetailsService;
	
	public User getUser(String email) {
		
		//deactivate student account which student's trial_date over
		new Thread(new Runnable() {
		    public void run() {
		    	studentService.deactivateStudentAccount();
		    }
		}).start();

		if( !email.equals("")) {
			if(isExistUser(email)) {
				return userRepository.findByEmail(email);
			}
		}
		return null;
	}
	
	public User userRegister(User user) {
		if(!isExistUser(user.getEmail())) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			user = userRepository.save(user);
			
			jwtInMemoryUserDetailsService.addNewUserInMemory(user);
			
			return user;
		}
		return null;
	}
	
	public String uploadUserProfileImage(MultipartFile file,Integer userId) {
		if(userRepository.existsById(userId)) {
			String keyName = getFileKeyName(userId);
			if(keyName!=null) {
				s3Service.uploadFile(keyName, file);
				return "success";
			}	
		}
		return null;
	}
	
	public ByteArrayOutputStream downLoadProfileImage(Integer userId) {
		if(userRepository.existsById(userId)) {
			String keyName = getFileKeyName(userId);
			if(keyName!=null) {
				return s3Service.downloadFile(keyName);
			}
		}
		return null;
	}
	
	//Helping function
	private Boolean isExistUser(String email) {
		User user=userRepository.findByEmail(email);
		if(user != null) {
			return true;
		}
		return false;
	}
	
	public String getFileKeyName(Integer userId) {
		User user = userRepository.findByUserId(userId);
		Integer studentId = studentRepository.findByUserId(user);
		if(studentId!=null) {
			String keyName = studentRepository.findByStudentId(studentId).getNic()+".jpg";
			return keyName;
		}
		return null;
	}
	
}
