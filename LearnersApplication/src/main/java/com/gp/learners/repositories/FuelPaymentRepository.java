package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.FuelPayment;

public interface FuelPaymentRepository extends JpaRepository<FuelPayment,Integer>{
	
	@Query(value="select * from fuel_payment where month = :month and year = :year",nativeQuery=true)
	public FuelPayment findByMonth(@Param("month")Integer month,@Param("year")Integer year);
	
	@Query(value="select sum(amount) from fuel_payment where month = :month and year = :year",nativeQuery=true)
	public Double findPaymentByMonth(@Param("month") Integer month,@Param("year") Integer year);
	
	@Query(value="select * from fuel_payment where year = :year order by month asc",nativeQuery=true)
	public List<FuelPayment> findByYear(@Param("year") Integer year);
	
	@Query(value="select fuel_payment_id from fuel_payment where year = :year order by month asc",nativeQuery=true)
	public List<Integer> findsalaryIdByYear(@Param("year") Integer year);
}
