package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime,Integer>{
	
	//select * from work_time where work_time_id = workTimeId
	public WorkTime findByWorkTimeId(Integer workTimeId);
}
