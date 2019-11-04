package com.gp.learners.repositories;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.Staff;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
	
	@Query(value="select * from attendance where staff_id = :staffId and date = :currentDate",nativeQuery=true)
	public Attendance findByStaffId(@Param("staffId") Integer staffId,@Param("currentDate") LocalDate date);
	
	@Query(value="select count(*) from attendance where hour(leave_time-come_time) >= :hours and MONTH(date) = :month and YEAR(date) = YEAR(current_date) and staff_id = :staffId",nativeQuery=true)
	public Integer findFullDaysByStaffId(@Param("hours") Integer hours ,@Param("staffId") Staff staffId ,@Param("month") Integer month);
	
	@Query(value="select count(*) from attendance where hour(leave_time-come_time) >= :hours and hour(leave_time-come_time) < :boundHour and MONTH(date) = :month and YEAR(date) = YEAR(current_date) and staff_id = :staffId",nativeQuery=true)
	public Integer findHalfDaysByStaffId(@Param("hours") Integer hours ,@Param("boundHour") Integer boundHour ,@Param("staffId") Staff staffId ,@Param("month") Integer month);

	@Query(value="select count(*) from attendance where hour(leave_time-come_time) < :hours and MONTH(date) = :month and YEAR(date) = YEAR(current_date) and staff_id = :staffId",nativeQuery=true)
	public Integer findNotCompleteDaysByStaffId(@Param("hours") Integer hours ,@Param("staffId") Staff staffId ,@Param("month") Integer month);
}
