package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.TimeSlot;

public interface InstructorRepository extends JpaRepository<Instructor,Integer>{
//	"SELECT instructor.ins_id,staff.name FROM package,vehicle_category,vehicle,instructor,staff WHERE "+
//		  	"package.cat_id=vehicle_category.cat_id AND vehicle.cat_id=vehicle_category.cat_id AND "+
//			"vehicle.ins_id=instructor.ins_id AND instructor.emp_id=staff.emp_id AND "+
//		  	"package.pac_id=? AND vehicle.transmission=? AND "+
//			"instructor.ins_id NOT IN (SELECT ins_id FROM lesson WHERE day=? AND time_slot_id=?)"
	@Query(value=
			"select * from instructor i,package p,vehicle_category c,vehicle v where "
			+ "p.vehicle_category_id = c.vehicle_category_id and "
			+ "v.vehicle_category_id = c.vehicle_category_id and "
			+ "v.instructor_id		 = i.instructor_id		 and "
			+ "p.package_id		= :packageId 	and "
			+ "v.transmission	= :transmission 	and "
			+ "i.instructor_id not in (select instructor_id from lesson l where l.day = :dayId and l.time_slot_id = :timeSlotId )"
			,nativeQuery=true)
	public List<Instructor> getRelevantInstructors(@Param("dayId")Integer dayId  ,@Param("packageId")Integer packageId	,@Param("timeSlotId")Integer timeSlotId  ,@Param("transmission")Integer transmission);
}
