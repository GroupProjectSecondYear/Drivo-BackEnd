package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.LessonDayFeedback;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.mapObject.LessonDayFeedbackChartDataMap;

public interface LessonDayFeedbackRepository extends JpaRepository<LessonDayFeedback, Integer>{
	
	@Query(value="select * from lesson_day_feedback l where l.student_package_id = :studentPackageId ",nativeQuery=true)
	public LessonDayFeedback findByStudentPackageId(@Param("studentPackageId") StudentPackage studentPackageId);
	
	@Query(
			"select count(*) from LessonDayFeedback  where " + 
			"studentPackageId.packageId = :packageId and " + 
			"studentPackageId.transmission = :transmission and " + 
			"studentPackageId.studentId.userId.status = 1 and " + 
			"time1 = :timeSlotId and "+
			"day1 = :day "
			)
	public Integer countDay1(@Param("packageId") com.gp.learners.entities.Package packageId,@Param("transmission") Integer transmission,@Param("timeSlotId") Integer timeSlotId,@Param("day") Integer day);

	@Query(
			"select count(*) from LessonDayFeedback  where " + 
			"studentPackageId.packageId = :packageId and " + 
			"studentPackageId.transmission = :transmission and " + 
			"studentPackageId.studentId.userId.status = 1 and " + 
			"time2 = :timeSlotId and "+
			"day2 = :day "
			)
	public Integer countDay2(@Param("packageId") com.gp.learners.entities.Package packageId,@Param("transmission") Integer transmission,@Param("timeSlotId") Integer timeSlotId,@Param("day") Integer day);
	
	
	@Query("from LessonDayFeedback where time1 = :timeSlotId or time2 = :timeSlotId")
	public List<LessonDayFeedback> findByTimeSlot(Integer timeSlotId);
}
