package com.im.learners.api.test;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.gp.learners.LearnersNewApplication;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LearnersNewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerIT {

	@org.springframework.boot.web.server.LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testRetrieveStudentCourse() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/students/1"),
				HttpMethod.GET, entity, String.class);

		System.out.println(response);
		String expected = "{id:Course1,name:Spring,description:10 Steps}";

		try {
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
}
