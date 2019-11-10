package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.AdminStaff;

public interface AdministrativeStaffRepository extends JpaRepository<AdminStaff,Integer>{


	//get AdminStaff by StaffId
			@Query(value="Select * from admin_staff where staff_id = :staffId",nativeQuery=true)
			public AdminStaff getAdminStaffByStaffId(@Param("staffId")Integer staffId);

}