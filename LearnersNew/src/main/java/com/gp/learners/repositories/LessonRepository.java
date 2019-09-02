package com.gp.learners.repositories;

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
}
