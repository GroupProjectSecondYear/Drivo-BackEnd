package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.gp.learners.entities.CourseFee;
import com.gp.learners.entities.StudentPackage;


public interface CourseFeeRepository extends JpaRepository<CourseFee, Integer>{
	
	@Query(value="select course_fee_id,amount,date,method from course_fee u WHERE u.student_package_id = :studentPackageId ",nativeQuery=true)
	public List<Object> findByStudentPackageId(@PathVariable("studentPackageId")StudentPackage studentPackageId);
	
	
}
