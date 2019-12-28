package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Maintainance;

public interface MaintainanceRepository extends JpaRepository<Maintainance, Integer>{
	
	@Query(value="select sum(amount) from maintainance where month(date) = :month and year(date) = :year",nativeQuery=true)
	public Double findPaymentByMonthAndYear(@Param("month") Integer month,@Param("year") Integer year);

}
