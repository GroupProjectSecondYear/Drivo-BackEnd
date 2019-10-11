package com.gp.learners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Vehicle;
import com.gp.learners.repositories.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	public Vehicle getVehicle(Integer vehicleId){
		if(vehicleRepository.existsById(vehicleId)) {
			return vehicleRepository.getVehicle(vehicleId);
		}
		return null;
	}
}
