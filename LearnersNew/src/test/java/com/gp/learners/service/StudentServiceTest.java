package com.gp.learners.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gp.learners.LearnersNewApplication;
import com.gp.learners.entities.CourseFee;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.User;
import com.gp.learners.entities.VehicleCategory;
import com.gp.learners.entities.mapObject.StudentPackageMap;
import com.gp.learners.repositories.CourseFeeRepository;
import com.gp.learners.repositories.ExamResultRepository;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnersNewApplication.class)
public class StudentServiceTest {

	@Autowired
	StudentService studentService;

	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private StudentPackageRepository studentPackageRepository;

	@MockBean
	PackageRepository packageRepository;

	@MockBean
	CourseFeeRepository courseFeeRepository;
	
	@MockBean
	ExamResultRepository examResultRepository;

	Student student = studentListProduce(1).get(0);
	User user = student.getUserId();
	Package package1 = packageProduce().get(0);
	StudentPackage studentPackage = studentPackageListProduce().get(0);

	Integer studentId = student.getStudentId();
	Integer userId = student.getUserId().getUserId();

	// Data Produce Method

	private List<Student> studentListProduce(Integer status) {

		List<Student> studentList = new ArrayList<Student>();

		Student student1 = new Student();
		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("Nuwan");
		user1.setLastName("Madhusanka");
		user1.setEmail("nu@gmail.com");
		user1.setAddress("Homagama");
		user1.setNic("980120232V");
		user1.setPassword("1234");
		user1.setProfileImage(0);
		user1.setRegDate(new Date());
		user1.setStatus(1);
		student1.setUserId(user1);
		student1.setStudentId(1);

		Student student2 = new Student();
		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("Asanka");
		user2.setLastName("Sujeewa");
		user2.setEmail("as@gmail.com");
		user2.setAddress("Homagama");
		user2.setNic("950120232V");
		user2.setPassword("1234");
		user2.setProfileImage(0);
		user2.setRegDate(new Date());
		user2.setStatus(1);
		student2.setUserId(user2);
		student2.setStudentId(2);

		if (status == 0) {
			user1.setStatus(0);
			user2.setStatus(0);
		}

		studentList.add(student1);
		studentList.add(student2);

		return studentList;
	}

	private List<StudentPackage> studentPackageListProduce() {
		List<StudentPackage> list = new ArrayList<StudentPackage>();
		StudentPackage studentPackage = new StudentPackage();

		studentPackage.setStudentId(student);
		studentPackage.setPackageId(packageProduce().get(0));
		studentPackage.setStudentPackageId(1);
		studentPackage.setTransmission(1);

		list.add(studentPackage);
		return list;
	}

	private List<Package> packageProduce() {
		List<Package> list = new ArrayList<Package>();

		Package package1 = new Package();
		package1.setAutoLes(10);
		package1.setBasicPayment(50000);
		package1.setDescription("");
		package1.setManualLes(14);
		package1.setPackageId(1);
		package1.setPackageImage(0);
		package1.setPrice(15000F);
		package1.setStatus(1);
		package1.setTitle("Car Package");
		package1.setVehicleCategoryId(vehicleCategoryProduce().get(0));

		list.add(package1);
		return list;
	}


	private List<VehicleCategory> vehicleCategoryProduce() {
		List<VehicleCategory> list = new ArrayList<VehicleCategory>();

		VehicleCategory v1 = new VehicleCategory();
		v1.setVehicleCategoryId(1);
		v1.setNumStudent(10);
		v1.setCategory("Car");

		list.add(v1);
		return list;
	}
	
	public LocalDate getLocalCurrentDate() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Colombo"));
		Date currentDate = calendar.getTime();
		LocalDate today = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return today;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////Testing////////////////////////////////////////////////////////////////

	@Test
	public void studentRegisterTest() {

		when(userRepository.findByEmailAndNic(student.getUserId().getEmail(), student.getUserId().getNic()))
				.thenReturn(user);
		when(studentRepository.save(student)).thenReturn(student);
		assertEquals(new Integer(1), studentService.studentRegister(student));// User Registration successful

		when(userRepository.findByEmailAndNic(student.getUserId().getEmail(), student.getUserId().getNic()))
				.thenReturn(null);
		assertEquals(new Integer(0), studentService.studentRegister(student));// User Registration Notsuccessfull

	}

	@Test
	public void getStudentListTest() {

		when(studentRepository.getStudent(0)).thenReturn(studentListProduce(0));
		assertEquals(2, studentService.getStudentList(0).size());
		assertEquals(new Integer(0), studentService.getStudentList(0).get(0).getUserId().getStatus());
	}

	@Test
	public void getStudentIdTest() {

		when(userRepository.existsById(userId)).thenReturn(true);// user Exist in the db
		when(userRepository.findByUserId(userId)).thenReturn(user);// get User data from db
		when(studentRepository.findByUserId(user)).thenReturn(studentId); // get student data from db
		assertEquals(student.getStudentId(), studentService.getStudentId(userId));

		when(userRepository.existsById(userId)).thenReturn(false);// user not exist in the db
		assertEquals(new Integer(-1), studentService.getStudentId(userId));

		when(studentRepository.findByUserId(user)).thenReturn(null);// student not exist in the db
		assertEquals(new Integer(-1), studentService.getStudentId(userId));

	}

	@Test
	public void getStudentDetailsTest() {

		when(studentRepository.existsById(studentId)).thenReturn(true);// student exist in the db
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		assertEquals(student, studentService.getStudentDetails(studentId));

		when(studentRepository.existsById(studentId)).thenReturn(false);// student not exist in the db
		assertEquals(null, studentService.getStudentDetails(studentId));
	}

	@Test
	public void studentUpdateTest() {

		List<User> userList = new ArrayList<User>();
		userList.add(user);

		when(userRepository.existsById(userId)).thenReturn(true);
		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(userRepository.findByUserId(userId)).thenReturn(user);
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		when(userRepository.findByNic(user.getNic())).thenReturn(user);
		when(studentRepository.save(student)).thenReturn(student);
		when(userRepository.getActiveUsers()).thenReturn(userList);
		assertEquals(new Integer(1), studentService.studentUpdate(student));// update successful

		Student student1 = studentListProduce(1).get(1);
		when(userRepository.findByEmail(user.getEmail())).thenReturn(student1.getUserId());
		assertEquals(new Integer(2), studentService.studentUpdate(student)); // same email has another person

		when(userRepository.findByNic(user.getNic())).thenReturn(student1.getUserId());
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		assertEquals(new Integer(3), studentService.studentUpdate(student)); // same NIC has another person

		when(userRepository.existsById(userId)).thenReturn(false);
		assertEquals(null, studentService.studentUpdate(student)); // user not exist in the db
	}

	@Test
	public void deleteStudentTest() {

		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentPackageRepository.packageListfindByStudentId(student)).thenReturn(null);// student not follow any
																							// package
		assertEquals("success", studentService.deleteStudent(studentId));// student delete from the db

		when(studentPackageRepository.packageListfindByStudentId(student)).thenReturn(studentPackageListProduce());// student
																													// follow
																													// any
																													// package
		assertEquals("error", studentService.deleteStudent(studentId));// student not delete from the db
	}

	@Test
	public void packageListIdTest() {

		List<Integer> studentpackageIdList = new ArrayList<Integer>();
		studentpackageIdList.add(1);

		when(studentRepository.getStudentId(studentId)).thenReturn(student);
		when(studentPackageRepository.findByStudentId(student)).thenReturn(studentpackageIdList);
		assertNotEquals(new Integer(-2), studentService.packageListId(studentId).get(0));
		assertNotEquals(new Integer(-1), studentService.packageListId(studentId).get(0));
	}

	@Test
	public void packageListTest() {

		List<Integer> studentpackageIdList = new ArrayList<Integer>();
		studentpackageIdList.add(1);

		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(studentRepository.findByStudentId(studentId)).thenReturn(student);
		when(studentPackageRepository.findByStudentId(student)).thenReturn(studentpackageIdList);
		when(packageRepository.findByPackageId(1)).thenReturn(packageProduce().get(0));

		assertEquals(1, studentService.packageList(studentId).size());
		assertEquals(new Integer(1), studentService.packageList(studentId).get(0).getPackageId());
	}

	@Test
	public void packageAddTest() {
		StudentPackageMap object = new StudentPackageMap(1, 1);
		Package package1 = packageProduce().get(0);

		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(packageRepository.existsById(package1.getPackageId())).thenReturn(true);
		when(studentPackageRepository.findByStudentIdAndPackageId(student, package1)).thenReturn(null);
		when(studentPackageRepository.save(new StudentPackage())).thenReturn(new StudentPackage());

		assertEquals("success", studentService.packageAdd(studentId, object));
	}

	// Should Check
	@Test
	public void packageDelete() {

		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(packageRepository.existsById(package1.getPackageId())).thenReturn(true);
		when(courseFeeRepository.findFeeForPackageByStudentIdAndPackageId(studentPackage.getStudentPackageId()))
				.thenReturn(0D);
		when(studentPackageRepository.findByStudentIdAndPackageId(student, package1)).thenReturn(studentPackage);
		assertEquals("success", studentService.packageDelete(studentId, package1.getPackageId()));

		when(courseFeeRepository.findFeeForPackageByStudentIdAndPackageId(1)).thenReturn(1000D);
		assertEquals("error", studentService.packageDelete(studentId, package1.getPackageId()));

	}

	@Test
	public void courseFeeListTest() {
		List<Object> courseFees = new ArrayList<Object>();
		courseFees.add(new Object());

		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(packageRepository.existsById(package1.getPackageId())).thenReturn(true);
		when(studentPackageRepository.findByStudentIdAndPackageId(student, package1)).thenReturn(new StudentPackage());
		when(studentPackageRepository.findByStudentPackageId(1)).thenReturn(new StudentPackage());
		when(courseFeeRepository.findByStudentPackageId(new StudentPackage())).thenReturn(courseFees);

		assertEquals(1, studentService.courseFeeList(studentId, 1).size());
	}
	
	@Test
	public void getStudentTrialListTest() {
		when(studentRepository.findByTrialDate(getLocalCurrentDate())).thenReturn(studentListProduce(1));
		assertEquals(student.getUserId().getNic(),studentService.getStudentTrialList(getLocalCurrentDate()).get(0).getNic());
	}
	
	@Test
	public void getStudentExamList() {
		when(studentRepository.findByExamDate(getLocalCurrentDate())).thenReturn(studentListProduce(1));
		assertEquals(student.getUserId().getNic(),studentService.getStudentExamList(getLocalCurrentDate()).get(0).getNic());
	}
	
	@Test
	public void getWrittenExamResultTest() {
		when(examResultRepository.findWrittenExamResultSum(new Date(),new Date(),2019)).thenReturn(50D);
		when(examResultRepository.findWrittenExamResultFailSum(new Date(),new Date(),2019)).thenReturn(50D);
		when(examResultRepository.findTrialExamResultSum(new Date(),new Date(),2019)).thenReturn(50D);
		when(examResultRepository.findTrialExamResultFailSum(new Date(),new Date(),2019)).thenReturn(50D); 
		
		assertEquals(12,studentService.getExamResult(1,2019).size());
//		assertEquals(new Double(50D),studentService.getExamResult(1, 2019).get(0));
	}

}
