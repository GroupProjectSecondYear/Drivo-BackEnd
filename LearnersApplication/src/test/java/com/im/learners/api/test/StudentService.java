package com.im.learners.api.test;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StudentService {
	
	@Test
	public void getStudetsList() {
		
		Response response = RestAssured.get("dd");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
	}
}
