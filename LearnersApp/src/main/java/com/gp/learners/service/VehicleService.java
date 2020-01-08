package com.gp.learners.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.Pdf;
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
	
	public List<Vehicle> getVehicleList(Integer status) {
		if(status>=0 && status<=1) {
			return vehicleRepository.getVehicleList(status);
		}
		return null;
	}
	
	
}
