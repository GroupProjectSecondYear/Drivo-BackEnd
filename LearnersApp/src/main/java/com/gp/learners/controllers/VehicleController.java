package com.gp.learners.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Vehicle;
import com.gp.learners.service.VehicleService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping("/vehicles")
	public void getVehicles() {
		
	}
	
	@GetMapping("/vehicle/{vehicleId}")
	public ResponseEntity<Vehicle> getVehicle(@PathVariable("vehicleId") Integer vehicleId) {
		Vehicle object =  vehicleService.getVehicle(vehicleId);
		if(object!=null) {
			return ResponseEntity.ok(object);
		}
		return ResponseEntity.notFound().build();
	}
}
