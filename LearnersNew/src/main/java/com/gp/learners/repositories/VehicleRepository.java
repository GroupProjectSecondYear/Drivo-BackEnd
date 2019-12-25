package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Vehicle;
import com.gp.learners.entities.VehicleCategory;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{
	
	@Query(value="select * from vehicle where vehicle_id = :vehicleId",nativeQuery=true)
	public Vehicle getVehicle(@Param("vehicleId") Integer vehicleId);
	
	
	@Query(value="select transmission from vehicle where vehicle_category_id = :vehicleCategoryId and transmission= :transmission and status=1 limit 1",nativeQuery=true)
	public Boolean findVehicleTransmission(@Param("vehicleCategoryId") Integer vehicleCategoryId,@Param("transmission") Integer transmission);


	@Query("from Vehicle where status = :status")
	public List<Vehicle> getVehicleList(@Param("status") Integer status);
	

	@Query(value="select * from vehicle u where vehicle_id = :vehicle_id",nativeQuery=true)
	public Vehicle findByVehicleId(@Param("vehicle_id") Integer vehicleId);


	//findStudent by UserId
		@Query(value="select vehicle_id from vehicle u WHERE u.vehicle_category_id = :vehicle_category_id ",nativeQuery=true)
		public Integer findByVehicleCategoryId(@Param("vehicle_category_id")VehicleCategory vehicleCategory);


	
}
