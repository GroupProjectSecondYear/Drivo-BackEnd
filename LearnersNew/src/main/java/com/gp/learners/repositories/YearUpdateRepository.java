package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gp.learners.entities.YearUpdate;

public interface YearUpdateRepository extends JpaRepository<YearUpdate, Integer>{
	
//	@Query(value="SELECT  * FROM year_update ORDER BY update_year DESC limit 1 ",nativeQuery=true)
//	public YearUpdate getLastRecord();
	
	@Query(value="select distinct(update_year) from year_update order by update_year ASC LIMIT 4",nativeQuery=true)
	public List<Integer> getYears();
}
