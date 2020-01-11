package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.InsurancePayment;
import com.gp.learners.entities.Vehicle;

public interface InsurancePaymentRepository extends JpaRepository<InsurancePayment, Integer>{
	
	@Query(value="select * from insurance_payment  where vehicle_id = :vehicleId",nativeQuery=true)
	public List<InsurancePayment> findByVehicleId(@Param("vehicleId") Integer vehicleId);
	
	@Query(value="select * from insurance_payment where vehicle_id = :vehicleId and year = :year",nativeQuery=true)
	public InsurancePayment findByVehicleIdAndYear(@Param("vehicleId") Integer vehicleId,@Param("year") Integer year);
	
	@Query(value="select sum(amount) from insurance_payment where month(date) = :month and year(date) = :year ",nativeQuery=true)
	public Double findPaymentByMonthAndYear(@Param("year") Integer year,@Param("month") Integer month);
}
