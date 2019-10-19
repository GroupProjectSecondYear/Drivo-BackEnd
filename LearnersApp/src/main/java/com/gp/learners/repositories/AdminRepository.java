package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

}
