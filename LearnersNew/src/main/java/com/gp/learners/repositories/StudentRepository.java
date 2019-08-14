package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

	
	//getstudent by id
	@Query("from Student where studentId = :studentId")
	public Student getStudentId(@Param("studentId")Integer studentId);
	
	
}
