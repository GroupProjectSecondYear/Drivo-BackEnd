package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{
	
	@Query(value="select * from vehicle where vehicle_id = :vehicleId",nativeQuery=true)
	public Vehicle getVehicle(@Param("vehicleId") Integer vehicleId);
}
