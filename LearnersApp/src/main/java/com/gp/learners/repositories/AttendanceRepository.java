package com.gp.learners.repositories;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gp.learners.entities.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
	
	@Query(value="select * from attendance where staff_id = :staffId and date = :currentDate",nativeQuery=true)
	public Attendance findByStaffId(@Param("staffId") Integer staffId,@Param("currentDate") LocalDate date);
}
