package com.gp.learners.service;



import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.config.security.JwtInMemoryUserDetailsService;
import com.gp.learners.entities.Admin;
import com.gp.learners.entities.Staff;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.AdminRepository;
import com.gp.learners.repositories.InstructorRepository;
import com.gp.learners.repositories.StaffRepository;
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
	StaffRepository staffRepository;
	
	@Autowired
	InstructorRepository instructorRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	JwtInMemoryUserDetailsService jwtInMemoryUserDetailsService;
	
	@Value("${aws.s3.bucket.profile_image}")
	private String bucketName;
	
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
				s3Service.uploadFile(keyName, file,bucketName);
				User user = userRepository.findByUserId(userId);
				user.setProfileImage(1);
				userRepository.save(user);
				return "success";
			}	
		}
		return null;
	}
	
	public ByteArrayOutputStream downLoadProfileImage(Integer userId) {
		if(userRepository.existsById(userId)) {
			String keyName = getFileKeyName(userId);
			if(keyName!=null) {
				User user = userRepository.findByUserId(userId);
				if(user.getProfileImage()==1) {
					return s3Service.downloadFile(keyName,bucketName);
				}else {
					return s3Service.downloadFile("defaultprofile.jpg",bucketName);
				}
				
			}
		}
		return null;
   }
	
   public String getUserName(Integer userId,Integer userRole) {
	   if(userRepository.existsById(userId)) {
		   if(userRole==1) {
			   Admin user = adminRepository.findByUserId(userRepository.findByUserId(userId));
			   return user.getName();
		   }else if(userRole==2 || userRole==3 || userRole==4) {
			   Staff user = staffRepository.findByUserId(userId);
			   return user.getName();
		   }else if(userRole==5) {
			   Integer studentId = studentRepository.findByUserId(userRepository.findByUserId(userId));
			   Student user = studentRepository.findByStudentId(studentId);
			   return user.getName();
		   }else {
			   return null;
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
		String keyName = user.getUserId()+".jpg";
		return keyName;	
	}
	
}
