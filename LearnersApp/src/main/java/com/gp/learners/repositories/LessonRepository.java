package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Path;
import com.gp.learners.entities.TimeSlot;

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
	
	@Query(value="select * from lesson l where l.lesson_id = :lessonId",nativeQuery=true)
	public Lesson findByLessonId(@Param("lessonId")Integer lessonId);
	
	@Query(value="select * from lesson l where l.day = :day and l.time_slot_id = :timeSlotId and l.instructor_id = :instructorId and l.status = 1 ",nativeQuery=true)
	public Lesson findByDeactivateLessonDetails(@Param("day") Integer day, @Param("timeSlotId") TimeSlot timeSlotId, @Param("instructorId") Instructor instructorId);
	
	@Query(value="select * from lesson l where l.day = :day and l.package_id = :packageId and l.time_slot_id = :timeSlotId and l.transmission = :transmission and status=1",nativeQuery=true)
	public List<Lesson> findLesson(@Param("day") Integer day, @Param("packageId") Package packageId,@Param("transmission") Integer transmission,@Param("timeSlotId") TimeSlot timeSlotId );
	
}
