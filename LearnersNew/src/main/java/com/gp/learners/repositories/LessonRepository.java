package com.gp.learners.repositories;

import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.SqlResultSetMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Path;
import com.gp.learners.entities.TimeSlot;
import com.gp.learners.entities.mapObject.LessonDistributionMap;

public interface LessonRepository extends JpaRepository<Lesson,Integer>{
	
	@Query(value=
			"(select * from lesson l where "
			+ "l.day 		   = :dayId and "
			+ "l.transmission  = :transmission and "
			+ "l.num_stu 	   = :numStu and "
			+ "l.instructor_id = :instructorId and "
			+ "l.time_slot_id  = :timeSlotId and "
			+ "l.package_id    = :packageId and "
			+ "l.path_id 	   = :pathId);"
			,nativeQuery=true)
	public Lesson findByLessonDetails(@Param("dayId")Integer dayId,
									  @Param("transmission")Integer transmission,
									  @Param("numStu")Integer numStu,
									  @Param("instructorId")Instructor instructorId,
									  @Param("timeSlotId")TimeSlot timeSlotId,
									  @Param("packageId")Package packageId,
									  @Param("pathId")Path pathId);
	
	
	@Query(value="select * from lesson l,time_slot t  where l.day = :day and l.time_slot_id=t.time_slot_id and l.status = :type ORDER BY t.start_time ASC",nativeQuery=true)
	public List<Lesson> getLessonASC(@Param("day")Integer day,@Param("type") Integer type);
	
	@Query(value="select * from lesson l,time_slot t  where l.day = :day and l.time_slot_id=t.time_slot_id and l.status = :type and l.instructor_id = :instructorId ORDER BY t.start_time ASC",nativeQuery=true)
	public List<Lesson> getLessonByInstructorId(@Param("day")Integer day,@Param("type") Integer type,@Param("instructorId") Integer instructorId);
	
	@Query(value="select * from lesson l,time_slot t  where l.day = :day and l.time_slot_id=t.time_slot_id and l.status = :type and l.package_id = :packageId and l.transmission = :transmission ORDER BY t.start_time ASC",nativeQuery=true)
	public List<Lesson> getLessonsASCByPackageId(@Param("day")  Integer day,@Param("type") Integer type, @Param("packageId") Package packageId,@PathVariable("transmission") Integer transmission);
	
	@Query(value="select distinct(l.time_slot_id) from lesson l,time_slot t  where  l.time_slot_id=t.time_slot_id and l.status = :type and l.package_id = :packageId and l.transmission = :transmission ORDER BY t.start_time ASC",nativeQuery=true)
	public List<Integer> getLessonTimeSlot(@Param("type") Integer type, @Param("packageId") Package packageId,@PathVariable("transmission") Integer transmission);
	
	@Query(value="select * from lesson l where l.lesson_id = :lessonId",nativeQuery=true)
	public Lesson findByLessonId(@Param("lessonId")Integer lessonId);
	
	@Query(value="select * from lesson l where l.day = :day and l.time_slot_id = :timeSlotId and l.instructor_id = :instructorId and l.status = 1 ",nativeQuery=true)
	public Lesson findByDeactivateLessonDetails(@Param("day") Integer day, @Param("timeSlotId") TimeSlot timeSlotId, @Param("instructorId") Instructor instructorId);
	
	@Query(value="select * from lesson l where l.day = :day and l.package_id = :packageId and l.time_slot_id = :timeSlotId and l.transmission = :transmission and status=1",nativeQuery=true)
	public List<Lesson> findLesson(@Param("day") Integer day, @Param("packageId") Package packageId,@Param("transmission") Integer transmission,@Param("timeSlotId") TimeSlot timeSlotId );
	
	@Query("select new com.gp.learners.entities.mapObject.LessonDistributionMap(u.day,count(u)) from Lesson u where u.packageId = :packageId and u.transmission = :transmission " + 
				 "and u.status = 1 group by u.day order by u.day ")
	public List<LessonDistributionMap> findByPackageIdAndTransmission(@Param("packageId") Package packageId,@Param("transmission") Integer transmission);
	
	@Query(value="select * from lesson l where l.package_id = :packageId and l.transmission = :transmission order by day ",nativeQuery=true)
	public List<Lesson> getLessonsfindByPackageIdAndTransmission(@PathVariable("packageId") Package packageId,@PathVariable("transmission") Integer transmission);
	
	@Query(value="select * from lesson l where l.day = :day and l.package_id = :packageId  and l.transmission = :transmission and status=1",nativeQuery=true)
	public List<Lesson> getTimeSlotListAccordingToDateAndPackage(@Param("day") Integer day, @Param("packageId") Package packageId,@Param("transmission") Integer transmission);
}
