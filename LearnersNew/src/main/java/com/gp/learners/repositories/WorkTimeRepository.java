package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime,Integer>{
	
	//select * from work_time where work_time_id = workTimeId
	public WorkTime findByWorkTimeId(Integer workTimeId);
	
	@Query(value="select * from work_time where year(update_date) = :currentYear and apply_month = :applyMonth",nativeQuery=true)
	public WorkTime findByYearAndMonth(@Param("currentYear")Integer year,@Param("applyMonth")Integer month);
	
	//this data display on the ADMIN work_time table
	@Query(value="select * from work_time order by update_date DESC limit 1",nativeQuery=true)
	public WorkTime findLastUpdateRecord();
	
	//use when salary calculate
	@Query(value="select * from work_time where " + 
			     "(year(update_date) = :year and apply_month < :month) or " + 
			     "(year(update_date) < :year) " + 
			     "order by update_date DESC limit 1",nativeQuery=true)
	public WorkTime findRelevantRecord(@Param("year") Integer year,@Param("month") Integer month);
}
