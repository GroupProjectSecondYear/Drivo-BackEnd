package com.gp.learners;

import static org.junit.Assert.assertEquals;
import org.springframework.boot.SpringApplication;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gp.learners.entities.Student;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=LearnersNewApplication.class)
public class LeanersNewApplicationTests {

	@Autowired
	StudentService studentService;
	
	@MockBean
	private StudentRepository studentRepository;
	
	List<Student> studentList = new ArrayList<Student>();
	
	@Test
	public void getStudentListTest() {
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
		studentList.add(student1);
		when(studentRepository.getStudent(1)).thenReturn(studentList);
		assertEquals(1,studentService.getStudentList(1).size());
	}

}
