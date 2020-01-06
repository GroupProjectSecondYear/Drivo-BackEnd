package com.gp.learners.repositories;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import org.omg.CORBA.IntHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gp.learners.entities.Attendance;
import com.gp.learners.entities.Staff;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
	
	@Query(value="select * from attendance where staff_id = :staffId and date = :currentDate",nativeQuery=true)
	public Attendance findByStaffId(@Param("staffId") Integer staffId,@Param("currentDate") LocalDate date);
	
	@Query(value="select count(*) from attendance where hour(leave_time-come_time) >= :hours and MONTH(date) = :month and YEAR(date) = :year and staff_id = :staffId",nativeQuery=true)
	public Integer findFullDaysByStaffId(@Param("hours") Integer hours ,@Param("staffId") Staff staffId ,@Param("month") Integer month,@Param("year") Integer year);
	
	@Query(value="select count(*) from attendance where hour(leave_time-come_time) >= :hours and hour(leave_time-come_time) < :boundHour and MONTH(date) = :month and YEAR(date) = :year and staff_id = :staffId",nativeQuery=true)
	public Integer findHalfDaysByStaffId(@Param("hours") Integer hours ,@Param("boundHour") Integer boundHour ,@Param("staffId") Staff staffId ,@Param("month") Integer month ,@Param("year") Integer year);

	@Query(value="select count(*) from attendance where hour(leave_time-come_time) < :hours and MONTH(date) = :month and YEAR(date) = :year and staff_id = :staffId",nativeQuery=true)
	public Integer findNotCompleteDaysByStaffId(@Param("hours") Integer hours ,@Param("staffId") Staff staffId ,@Param("month") Integer month,@Param("year") Integer year);
	
	@Query(value="select * from attendance where staff_id = :staffId and MONTH(date) = :month and YEAR(date) = :year",nativeQuery=true)
	public List<Attendance> findByStaffIdAndMonth(@Param("staffId")Staff staffId ,@Param("month")Integer month, @Param("year") Integer year);

	@Query(value="select attendance_id from attendance where year(date) = :year",nativeQuery=true)
	public List<Integer> findAttendanceIdByYear(@Param("year") Integer year);
}
