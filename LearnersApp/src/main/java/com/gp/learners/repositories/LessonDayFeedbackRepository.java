package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.LessonDayFeedback;
import com.gp.learners.entities.StudentPackage;
import com.gp.learners.entities.mapObject.LessonDayFeedbackChartDataMap;

public interface LessonDayFeedbackRepository extends JpaRepository<LessonDayFeedback, Integer>{
	
	@Query(value="select * from lesson_day_feedback l where l.student_package_id = :studentPackageId ",nativeQuery=true)
	public LessonDayFeedback findByStudentPackageId(@Param("studentPackageId") StudentPackage studentPackageId);
	
	@Query(
			"select new com.gp.learners.entities.mapObject.LessonDayFeedbackChartDataMap(l.day1,count(l)) from LessonDayFeedback l ,StudentPackage s , User u,Student t where " + 
			"l.studentPackageId = s.studentPackageId and " + 
			"s.studentId = t.studentId and " + 
			"t.userId = u.userId and " + 
			"u.status=1 and " + 
			"s.packageId = :packageId and "+
			"s.transmission = :transmission and " + 
			"l.time1 = :time "+
			"group by l.day1 "
			)
	public List<LessonDayFeedbackChartDataMap> CountDay1(@Param("packageId") com.gp.learners.entities.Package packageId,@Param("transmission") Integer transmission,@Param("time") Integer time);

	@Query(
			"select new com.gp.learners.entities.mapObject.LessonDayFeedbackChartDataMap(l.day2,count(l)) from LessonDayFeedback l ,StudentPackage s , User u,Student t where " + 
			"l.studentPackageId = s.studentPackageId and " + 
			"s.studentId = t.studentId and " + 
			"t.userId = u.userId and " + 
			"u.status=1 and " + 
			"s.packageId = :packageId and "+
			"s.transmission = :transmission and " + 
			"l.time2 = :time "+
			"group by l.day2 "
			)
	public List<LessonDayFeedbackChartDataMap> CountDay2(@Param("packageId") com.gp.learners.entities.Package packageId,@Param("transmission") Integer transmission,@Param("time") Integer time);
}
