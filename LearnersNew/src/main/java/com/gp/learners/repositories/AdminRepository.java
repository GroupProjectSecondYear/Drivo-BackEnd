package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Admin;
import com.gp.learners.entities.User;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	public Admin findByUserId(User userId);
	
}
