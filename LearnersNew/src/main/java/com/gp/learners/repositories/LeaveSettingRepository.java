package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.LeaveSetting;

public interface LeaveSettingRepository extends JpaRepository<LeaveSetting, Integer>{
	
	@Query(value="select * from leave_setting order by update_date DESC limit 1",nativeQuery=true)
	public LeaveSetting findLastRecord();
	
	@Query(value="select * from leave_setting where year(update_date) = :currentYear and apply_month = :applyMonth",nativeQuery=true)
	public LeaveSetting findByYearAndMonth(@Param("currentYear") Integer year,@Param("applyMonth")Integer month);

	//use when salary calculation
	@Query(value="select num_leave from leave_setting where " + 
				 "(year(update_date) = :year and apply_month < :month) or " + 
				 "(year(update_date) < :year) " + 
				 "order by update_date DESC limit 1",nativeQuery=true)
	public Integer findRelevantRecord(@Param("year") Integer year,@Param("month") Integer month);
}
