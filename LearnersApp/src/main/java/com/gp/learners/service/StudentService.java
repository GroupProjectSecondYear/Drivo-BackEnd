package com.gp.learners.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gp.learners.config.security.JwtInMemoryUserDetailsService;
import com.gp.learners.entities.CourseFee;
import com.gp.learners.entities.ExamResult;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.User;
import com.gp.learners.entities.mapObject.PaymentEmailBodyMap;
import com.gp.learners.entities.mapObject.StudentPackageMap;
import com.gp.learners.entities.mapObject.StudentTrialMap;
import com.gp.learners.repositories.CourseFeeRepository;
import com.gp.learners.repositories.ExamResultRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentLessonRepository;
import com.gp.learners.repositories.StudentNotificationRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.UserRepository;

import java.time.LocalDate;

@Service
public class StudentService {

	@Autowired
	StudentPackageRepository studentPackageRepository;

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	CourseFeeRepository courseFeeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ExamResultRepository examResultRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	TimeTableService timeTableService;
	
	@Autowired
	StudentLessonRepository studentLessonRepository;
	
	@Autowired
	StudentNotificationRepository studentNotificationRepository;
	
	@Autowired
	JwtInMemoryUserDetailsService jwtInMemoryUserDetailsService;
	
	
	public Integer studentRegister(Student student) {
		if( isExistUser(student.getUserId().getNic(),student.getUserId().getEmail())) {
			studentRepository.save(student);
			return 1;
		}
		return 0;
	}
	
	//getStudentList
	public List<Student> getStudentList(Integer status){
		return studentRepository.getStudent(status);
	}
	
	//get StudentId
	public Integer getStudentId(Integer userId) {
		if(userRepository.existsById(userId)) {
			Integer studentId=studentRepository.findByUserId(userRepository.findByUserId(userId));
			if(studentId != null) {
				return studentId;
			}
		}
		return -1;
	}
	
	//get Student Details
	public Student getStudentDetails(Integer studentId) {
		if(studentId != null) {
			if(studentRepository.existsById(studentId)) {
				return studentRepository.findByStudentId(studentId);
			}
		}
		return null; 
	}
	
	//update Student Details
	public Integer studentUpdate(Student student) {
		
		Boolean isPasswordChanged=false;
		if(userRepository.existsById(student.getUserId().getUserId()) && studentRepository.existsById(student.getStudentId())) {
			Integer userId = student.getUserId().getUserId();
			Integer studentId = student.getStudentId();
			User newUser = student.getUserId();
			User currentUser = userRepository.findByUserId(userId);
			
			//check password change or not.If password change change then encode the password.
			String newPassword = newUser.getPassword();
			String currentPassword = currentUser.getPassword();
			if(!currentPassword.equals(newPassword)) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				newPassword = encoder.encode(newPassword);
				newUser.setPassword(newPassword);
				isPasswordChanged=true;
			}
			
			//check update email is unique
			String email = newUser.getEmail();
			User user1 = userRepository.findByEmail(email);
			if(user1 != null && !user1.getUserId().equals(userId)) {
				return 2;//Same Email has another person.Save unsuccessful
			}else {
				
				//check update nic has another person
				String nic = student.getUserId().getNic();
				User user2= userRepository.findByNic(nic);
				if(user2 != null && !user2.getUserId().equals(userId)) {
					return 3;//same nic has another person.Save unsuccessful
				}else {
					studentRepository.save(student);
					if(isPasswordChanged) {
						jwtInMemoryUserDetailsService.setUserInMemory();
					}
					return 1;//save successful
				}
			}
			
			
		}
		return null;
	}
	
	//delete Student
	public String deleteStudent(Integer studentId) {
		if(studentId != null) {
			if(studentRepository.existsById(studentId)) {
				studentRepository.deleteById(studentId);
				return "success";
			}
		}
		return "error";
	}

	// give student followed packagesId
	public List<Integer> packageListId(Integer studentId) {

		List<Integer> list = new ArrayList<Integer>();// error list

		Student student = studentRepository.getStudentId(studentId);
		if (student != null) {
			List<Integer> packId = studentPackageRepository.findByStudentId(student);
			if (packId != null && packId.size() > 0) {
				return packId;
			}
			list.add(-2);// if no any packages that student follow
			return list;
		}

		// if no such a student
		list.add(-1);
		return list;
	}
	
	//give student following packages
	public List<Package> packageList(Integer studentId){
		List<Package> packages=new ArrayList<Package>();
		if(studentId != null) {
			if(studentRepository.existsById(studentId)) {
				Student student=getStudent(studentId);
				if(existStudentId(student)) {//check student record has in the studentPackage table
					List<Integer> packageId=studentPackageRepository.findByStudentId(student);
					for( Integer i : packageId) {
						packages.add(packageRepository.findByPackageId(i));
					}
					return packages;
					
				}
			}
		}
		return packages;
	}

	// Add Student following package details to the db
	public String packageAdd(Integer studentId, StudentPackageMap object) {
		
			StudentPackage studentPackageObject=new StudentPackage();
		
			//check whether (student && package)exist or not
			if(studentRepository.existsById(studentId) && packageRepository.existsById(object.getPackageId())) {
				
				//check if already student register this course(Package) or not
				//if not register for course
				if(notExistStudentIdAndPackageId(getStudent(studentId),getPackage(object.getPackageId()))) {
					
					//create studentpackage object
					studentPackageObject.setJoinDate(LocalDate.now());
					studentPackageObject.setPackageId(getPackage(object.getPackageId()));
					studentPackageObject.setStudentId(getStudent(studentId));
					studentPackageObject.setTransmission(object.getTransmission());
					
					//save studentpackage object to database
					studentPackageRepository.save(studentPackageObject);
					return "success";
				}
				
			}

		return "notsuccess";
		
	}
	
	
	//Delete Student's package details from the db
	public String packageDelete(Integer stuId,Integer pacId) {
		if(stuId != null && pacId != null) {
			if(studentRepository.existsById(stuId) && packageRepository.existsById(pacId)) {//check whether student and package data represent the relevant tables
				if(!notExistStudentIdAndPackageId(getStudent(stuId), getPackage(pacId))) {//if relevant record exist in the studentPackage table
					
					//delete the studentPackage data(when delete studentpackage data relevant 'course fee' also deleted
					studentPackageRepository.deleteById(getStudentPackageId(stuId, pacId));
					return "success";
				}
			}
		}
		return "error";
	}
	
	//Get Student Course Fees Details
	public List<Object> courseFeeList(Integer studentId,Integer packageId){
		List<Object> courseFees=new ArrayList<Object>();
		if(studentId != null && packageId != null) {
			if(studentRepository.existsById(studentId) && packageRepository.existsById(packageId)) {//check whether student's and package's record has or not
				if(!notExistStudentIdAndPackageId(getStudent(studentId), getPackage(packageId))) {//if record exist in the studentPackage table
					
					Integer studentPackageId=getStudentPackageId(studentId, packageId);
					StudentPackage studentPackage=studentPackageRepository.findByStudentPackageId(studentPackageId);
					courseFees =courseFeeRepository.findByStudentPackageId(studentPackage);
					return courseFees;
				}
			}
		}
		return courseFees;
	}
	
	//Add Course Fee Details
	/*
	 * Return values
	 * 1 -> Payment Success + Email Send
	 * 2 -> Payment Success + Email Not Send
	 * -1 -> Payment Not Success
	 * 
	 */
	public Integer courseFeeAdd(Integer studentId,Integer packageId,CourseFee object) {
		if(!notExistStudentIdAndPackageId(getStudent(studentId), getPackage(packageId))) {
			
			if(object.getAmount()>0) {
				
				//get Course Fee
				Package packageObject=getPackage(packageId);
				Float courseFee=packageObject.getPrice();
				
				StudentPackage studentPackage=studentPackageRepository.findByStudentIdAndPackageId(getStudent(studentId), getPackage(packageId));//get StudentPAckage object
				Float payment=courseFeeRepository.getTotalFee(studentPackage);//Already done payments
				
				Float balance;
				if(payment != null) {
					balance=courseFee-payment;
				}else {
					balance=courseFee;
				}
				
				if(balance>0) {
					//newPayment save to db
					object.setStudentPackageId(studentPackage);
					courseFeeRepository.save(object);
					
					//send email for student
					User user = studentRepository.getStudentId(studentId).getUserId();
					
					String from="drivo@gmail.com";
					String to=user.getEmail();
					String subject="Package Payment of "+packageObject.getTitle()+" in Drivo Learners";
					
					PaymentEmailBodyMap message = new PaymentEmailBodyMap();
					message.setTitle(packageObject.getTitle());
					message.setDate(timeTableService.getLocalCurrentDate());
					message.setAmount(object.getAmount());
					
					String reply=emailService.setUpEmailInstance(from, to , subject , message);
					if(reply.equals("success")) {
						return 1;
					}else {
						return 2;
					}
					
				}
				
			}
			
		}
		return 0;
	}
	
	
	//get Trial Students Information
	public List<StudentTrialMap> getStudentTrialList(LocalDate localDate){
		
		
		List<Student> studentList=studentRepository.findByTrialDate(localDate);
		List<StudentTrialMap> studentTrialList=new ArrayList<StudentTrialMap>();
		
		for(Student student : studentList) {
			String studentName = student.getUserId().getFirstName()+" "+student.getUserId().getLastName();
			studentTrialList.add(new StudentTrialMap(studentName,student.getUserId().getNic()));
		}
		
		return studentTrialList;
	}
	
	//get Exam Students Information
	public List<StudentTrialMap> getStudentExamList(LocalDate localDate){
		
		
		List<Student> studentList=studentRepository.findByExamDate(localDate);
		List<StudentTrialMap> studentExamList=new ArrayList<StudentTrialMap>();
		
		for(Student student : studentList) {
			String studentName = student.getUserId().getFirstName()+" "+student.getUserId().getLastName();
			studentExamList.add(new StudentTrialMap(studentName,student.getUserId().getNic()));
		}
		
		return studentExamList;
	}
	
	
	//get WrittenExam Result
	/*
	 * type 1-->Written Exam
	 * 		2-->Trial Exam
	 */
	public List<Double> getWrittenExamResult(Integer type){
		
		List<Double> examResult=new ArrayList<Double>();
			
		Calendar gc = new GregorianCalendar();
     
        for(int i=0 ; i<12 ; i++) {
        	 gc.set(Calendar.MONTH, i);
             gc.set(Calendar.DAY_OF_MONTH, 0);
             Date monthStart = gc.getTime();//month start date
             gc.add(Calendar.MONTH, 1);
             gc.add(Calendar.DAY_OF_MONTH, 0);
             Date monthEnd = gc.getTime();//month end date
             
             Double pass=0d;
             Double fail=0d;
             
             if(type==1) {
            	 pass=examResultRepository.findWrittenExamResultSum(monthStart,monthEnd);
                 fail=examResultRepository.findWrittenExamResultFailSum(monthStart, monthEnd);
             }else {
            	 pass=examResultRepository.findTrialExamResultSum(monthStart,monthEnd);
                 fail=examResultRepository.findTrialExamResultFailSum(monthStart, monthEnd);
             }
             
             Double passRate;
             if(pass != null) {
            	 if(fail != null) {
            		 passRate=(pass/(pass+fail))*100;
            	 }else {
            		 passRate=100.d;
            	 }
            	 examResult.add(truncate(passRate, 2));
             }else {
            	 examResult.add(0.d);
             }        
        }
		return examResult;
	}
	
	
	//submit writtenExam Data
	public String submitWrittenExamResult(LocalDate localDate,Integer countPass,Integer countFail) {
		if( (localDate != null) && (countPass>=0) && (countFail>=0)) {
			if(existByDate(localDate)) {
				ExamResult object=examResultRepository.findByDate(localDate);
				object.setWrittenExam(countPass);
				object.setWrittenExamFail(countFail);
				examResultRepository.save(object);
			}else {
				ExamResult object=new ExamResult();
				object.setDate(localDate);
				object.setWrittenExam(countPass);
				object.setWrittenExamFail(countFail);
				object.setTrialExam(0);
				object.setTrialExamFail(0);
				examResultRepository.save(object);
			}
			
			return "success";
		}
		
		return "notsuccess";
	}
	
	//submit trialExam Data
	
	public String submitTrialExamResult(LocalDate localDate,Integer countPass,Integer countFail) {
			if( (localDate != null) && (countPass>=0) && (countFail>=0)) {
				if(existByDate(localDate)) {
					ExamResult object=examResultRepository.findByDate(localDate);
					object.setTrialExam(countPass);
					object.setTrialExamFail(countFail);
					examResultRepository.save(object);
				}else {
					ExamResult object=new ExamResult();
					object.setDate(localDate);
					object.setTrialExam(countPass);
					object.setTrialExamFail(countFail);
					object.setWrittenExam(0);
					object.setWrittenExamFail(0);
					examResultRepository.save(object);
				}
				
				return "success";
			}
			
			return "notsuccess";
	}
	
	//get Student data by using userid
	public Student getStudentData(Integer userId) {
		Student student=new Student();
		
		if(userRepository.existsById(userId)) {
			Integer studentId=studentRepository.findByUserId(userRepository.findByUserId(userId));
			student=studentRepository.findByStudentId(studentId);
			return student;
		}
		
		return student;
	}
	
	@Transactional
	public String deactivateStudentAccount() {
		try {
			List<Student> studentList = studentRepository.findByDate(timeTableService.getLocalCurrentDate());
			
			for (Student student : studentList) {
				
				student.setExamDate(null);
				student.setTrialDate(null);
				studentRepository.save(student);
				
				User object = student.getUserId();
				object.setStatus(0);
				userRepository.save(object);
				
				//delete studentLesson Details
				//studentLessonRepository.deleteByStudentId(student);
				
				//update JWT UserList
				jwtInMemoryUserDetailsService.setUserInMemory();
			}
			
		} catch (Exception e) {
			System.out.println("------------------------");
			System.out.println("There is a problem of student Serivce's deactivateStudentAccount() function");
			System.out.println(e.getMessage());
			System.out.println("------------------------");
			return "success";
		}
		return "success";
	}
	
	public Integer activateStudentAccount(Integer studentId) {
		
		if(studentRepository.existsById(studentId)) {
			Student student = studentRepository.findByStudentId(studentId);
			
			//checkCourse Fees Complete or not
			if(isAllCourseFeesComplete(student)) {
				User user = student.getUserId();
				user.setStatus(1);
				user = userRepository.save(user);
				
				jwtInMemoryUserDetailsService.addNewUserInMemory(user);
				
				return 1;
			}else {
				return 0;
			}
		}
		return null;
	}
	
	public List<Student> getpaymentNotCompleteStudent(){
		
		List<Student> courseFeeNotCompleteStudentList = new ArrayList<Student>();
		
		//get student who has trial examination this week or next week
		List<Student> studentList = studentRepository.findTrialExaminationStudentByWeek(timeTableService.getLocalCurrentDate());
		for (Student student : studentList) {
			if(!isAllCourseFeesComplete(student)) {
				courseFeeNotCompleteStudentList.add(student);
			}
		}
		
		return courseFeeNotCompleteStudentList;
	}
	
	public String continueStudentAccount(Integer studentId) {
		if(studentRepository.existsById(studentId)) {
			Student student = studentRepository.findByStudentId(studentId);
			if(student!=null) {
				User user = student.getUserId();
				if(user!=null) {
					user.setStatus(1);
					userRepository.save(user);
					jwtInMemoryUserDetailsService.addNewUserInMemory(user);
					return "success";
				}
			}
		}
		return null;
	}
	
	public StudentPackage getStudentPackageData(Integer id,Integer role,Integer packageId) {
		if(role==5) {
			if(userRepository.existsById(id)) {
				id = studentRepository.findByUserId(userRepository.findByUserId(id));
			}
		}
		
		if(studentRepository.existsById(id) && packageRepository.existsById(packageId)) {
			Student student = studentRepository.findByStudentId(id);
			Package packageData = packageRepository.findByPackageId(packageId);
			return studentPackageRepository.findByStudentIdAndPackageId(student, packageData);
		}
		return null;
	}
	
	//Helping Function
	
	//if student and package exist the student Package table return false
	private Boolean notExistStudentIdAndPackageId(Student studentObject,Package packageObject) {
		StudentPackage object=studentPackageRepository.findByStudentIdAndPackageId(studentObject, packageObject);
		if(object != null) {
			return false;
		}
		return true;
	}
	
	//if student exist in the studentpackage table return true
	private Boolean existStudentId(Student student) {
		List<Integer> object=studentPackageRepository.findByStudentId(student);
		if(object != null) {
			return true;
		}
		return false;
	}
	
	//
	private Integer getStudentPackageId(Integer studentId,Integer packageId) {
		StudentPackage object =studentPackageRepository.findByStudentIdAndPackageId(getStudent(studentId), getPackage(packageId));
		return object.getStudentPackageId();
	}
	
	//get student object from the student table
	private Student getStudent(Integer studentId) {
		return studentRepository.findByStudentId(studentId);
	}
	
	//get package object from the table
	private Package getPackage(Integer packageId) {
		return packageRepository.findByPackageId(packageId);
	}
	
	double truncate(double number, int precision)
	{
	    double prec = Math.pow(10, precision);
	    int integerPart = (int) number;
	    double fractionalPart = number - integerPart;
	    fractionalPart *= prec;
	    int fractPart = (int) fractionalPart;
	    fractionalPart = (double) (integerPart) + (double) (fractPart)/prec;
	    return fractionalPart;
	}
	
	private Boolean existByDate(LocalDate localDate) {
		ExamResult object=examResultRepository.findByDate(localDate);
		if(object != null) {
			return true;
		}
		return false;
	}
	
	private Boolean isExistUser(String nic,String email) {
		User user = userRepository.findByEmailAndNic(email,nic);
		if(user != null ) {
			return true;
		}
		return false;
	}
	
	
	@Transactional
	private Boolean isAllCourseFeesComplete(Student student) {

		Boolean flag =false;
		List<Integer> studentPackageList = studentPackageRepository.findByStudentId(student);
		
		for (Integer packageId : studentPackageList) {
			StudentPackage object = studentPackageRepository.findByStudentIdAndPackageId(student, packageRepository.findByPackageId(packageId));
			Float courseFee = object.getPackageId().getPrice();
			Float totalPay = courseFeeRepository.getTotalFee(object);
			
		
			if( (totalPay==null) || (courseFee != totalPay) && (courseFee>totalPay)) {
				flag=true;
				break;
			}
		}
		
		if(flag) {//course fee not completed
			return false;
		}else {
			//previous course fees completed.so delete all payment record
			for (Integer packageId : studentPackageList) {		
				StudentPackage object = studentPackageRepository.findByStudentIdAndPackageId(student, packageRepository.findByPackageId(packageId));
				courseFeeRepository.deleteByStudentPackageId(object);
			}
			
			//delete student previous lesson details
			studentLessonRepository.deleteByStudentId(student);
		}
		return true;
	}
	
	@Transactional
	public String clearStudentPreviousPayment(Integer studentId) {
		if(studentRepository.existsById(studentId)) {			
			Student student = studentRepository.findByStudentId(studentId);
			List<Integer> studentPackageList = studentPackageRepository.findByStudentId(student);
			for (Integer packageId : studentPackageList) {
				StudentPackage object = studentPackageRepository.findByStudentIdAndPackageId(student,packageRepository.findByPackageId(packageId));
				courseFeeRepository.deleteByStudentPackageId(object);
			}
			
			//delete student previous lesson details
			studentLessonRepository.deleteByStudentId(student);
			
			User user = student.getUserId();
			user.setStatus(1);
			userRepository.save(user);
			
			jwtInMemoryUserDetailsService.addNewUserInMemory(user);
			
			return "success";
		}
		return "null";
	}
	
}
