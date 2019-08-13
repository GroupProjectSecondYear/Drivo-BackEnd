package com.gp.learners.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gp.learners.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	
	User findByEmailAndPassword(String email,String password);
	
	List<User> findUserIdStaffIdNameRoleStatusByRoleNot(Integer role);
}
