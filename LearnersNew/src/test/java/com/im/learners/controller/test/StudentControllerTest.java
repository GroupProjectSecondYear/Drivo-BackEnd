package com.im.learners.controller.test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gp.learners.LearnersNewApplication;
import com.gp.learners.controllers.StudentController;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.User;
import com.gp.learners.service.StudentService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=LearnersNewApplication.class)
public class StudentControllerTest {

	MockMvc mocMvc;

	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	StudentController studentController;
	

	@MockBean
	StudentService studentService;

	// 1)Test the getStudetsList() Method
	private List<Student> studentList;

	@Before
	public void setup1() throws Exception {
		this.mocMvc = standaloneSetup(this.studentController).build();

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
	}

	@Test
	public void testGetStudetsListMethod() throws Exception {
		when(studentService.getStudentList(any(Integer.class))).thenReturn(studentList);
		mocMvc.perform(get("/students/1").contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk());
	}
}
