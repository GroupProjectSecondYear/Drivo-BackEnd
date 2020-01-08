package com.gp.learners.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Instructor;
import com.gp.learners.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	@Query(value = "select * from vehicle where vehicle_id = :vehicleId", nativeQuery = true)
	public Vehicle getVehicle(@Param("vehicleId") Integer vehicleId);

	@Query(value = "select transmission from vehicle where vehicle_category_id = :vehicleCategoryId and transmission= :transmission and status=1 limit 1", nativeQuery = true)
	public Boolean findVehicleTransmission(@Param("vehicleCategoryId") Integer vehicleCategoryId,
			@Param("transmission") Integer transmission);

	@Query("from Vehicle where status = :status")
	public List<Vehicle> getVehicleList(@Param("status") Integer status);

	// change instructor assigned to vehicle
	@Modifying
	@Query(value = "update vehicle set instructor_id=:new_instructor_id where instructor_id=:instructor_id", nativeQuery = true)
	public int updateInstructorofVehicle(@Param("instructor_id") Integer instructor_id,
			@Param("new_instructor_id") Integer new_instructor_id);

	@Query(value = "select vehicle_Id from vehicle where instructor_id=:instructorId", nativeQuery = true)
	public Integer getVehicleofInstructor(@Param("instructorId") Integer instructorId);

	// assign a instructor to vehicle
	@Modifying
	@Transactional
	@Query(value = "update vehicle set instructor_id=:instructor_id where vehicle_id=:vehicle_id", nativeQuery = true)
	public Boolean assignInstructorforVehicle(@Param("vehicle_id") Integer vehicle_id);

}
