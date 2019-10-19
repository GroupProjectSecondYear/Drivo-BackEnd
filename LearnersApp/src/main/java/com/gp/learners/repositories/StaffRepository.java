package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.gp.learners.entities.Staff;



public interface StaffRepository extends JpaRepository<Staff, Integer>{
		
	@Query(value="select * from staff s where s.user_id = :userId ",nativeQuery=true)
	public Staff  findByUserId(@PathVariable("userId")Integer userId);
	
	@Query(value="select * from staff where staff_id = :staffId",nativeQuery=true)
	public Staff findByStaffId(@Param("staffId")Integer staffId);
}
