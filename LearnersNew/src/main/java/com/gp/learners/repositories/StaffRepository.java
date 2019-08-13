package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.Staff;



public interface StaffRepository extends JpaRepository<Staff, Integer>{
	
}
