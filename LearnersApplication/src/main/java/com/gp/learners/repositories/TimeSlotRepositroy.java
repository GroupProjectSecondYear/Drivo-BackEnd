package com.gp.learners.repositories;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.TimeSlot;


public interface TimeSlotRepositroy extends JpaRepository<TimeSlot, Integer>{

	@Query(value="select * from time_slot u where u.start_time = :startTime and u.finish_time = :finishTime",nativeQuery=true)
	public TimeSlot findByStartTimeandFinishTime(@Param("startTime")LocalTime startTime,@Param("finishTime") LocalTime finishTime);
	
	@Query(value="select * from time_slot u where u.time_slot_id = :timeSlotId",nativeQuery=true)
	public TimeSlot findByTimeSlotId(@Param("timeSlotId")Integer timeSlotId);
	
	@Query("from TimeSlot order by startTime ASC")
	public List<TimeSlot> getAsc();
}

