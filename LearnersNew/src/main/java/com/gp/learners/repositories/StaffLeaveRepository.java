package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.StaffLeave;
import com.gp.learners.entities.Staff;

public interface StaffLeaveRepository extends JpaRepository<StaffLeave, Integer>{

	@Query(value="select count(*) from staff_leave where MONTH(date) = :month and YEAR(date) = :year and staff_id = :staffId",nativeQuery=true)
	public Integer findByStaffIdAndMonth(@Param("staffId") Staff staffId ,@Param("month") Integer month,@Param("year") Integer year);
	
	@Query(value="select count(*) from staff_leave where YEAR(date) = :year and staff_id = :staffId",nativeQuery=true)
	public Integer findByStaffId(@Param("staffId") Staff staffId,@Param("year") Integer year);
	
	@Query(value="select staff_leave_id from staff_leave where YEAR(date) = :year",nativeQuery=true)
	public List<Integer> findStaffLeaveIdByYear(@Param("year") Integer year);
}
