package com.gp.learners.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.FuelPayment;
import com.gp.learners.entities.InsurancePayment;
import com.gp.learners.entities.Vehicle;
import com.gp.learners.service.VehicleService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@GetMapping("/vehicles/{status}")
	public List<Vehicle> getVehicles(@PathVariable("status") Integer status) {
		return vehicleService.getVehicleList(status);
	}
	
	@GetMapping("/vehicle/{vehicleId}")
	public ResponseEntity<Vehicle> getVehicle(@PathVariable("vehicleId") Integer vehicleId) {
		Vehicle object =  vehicleService.getVehicle(vehicleId);
		if(object!=null) {
			return ResponseEntity.ok(object);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("vehicle/insurance/{vehicleId}")
	public ResponseEntity<List<InsurancePayment>> getVehicleInsurancePaymentDetails(@PathVariable("vehicleId") Integer vehicleId){
		List<InsurancePayment> insurancePaymentList = vehicleService.getVehicleInsurancePaymentDetails(vehicleId);
		if(insurancePaymentList!=null) {
			return ResponseEntity.ok(insurancePaymentList);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("vehicle/insurance/{vehicleId}")
	public ResponseEntity<Integer> addInsurancePayment(@PathVariable("vehicleId") Integer vehicleId,@RequestBody InsurancePayment object){
		Integer reply = vehicleService.addInsurancePayment(vehicleId, object);
		if(reply!=null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("vehicle/fuel/{year}")
	public ResponseEntity<List<FuelPayment>> getVehicleFuelData(@PathVariable("year") Integer year){
		return ResponseEntity.ok(vehicleService.getVehicleFuelData(year));
	}
	
	@PostMapping("vehicle/fuel/{userId}")
	public ResponseEntity<Void> addVehicleFuelData(@PathVariable("userId") Integer userId, @RequestBody FuelPayment object){
		Integer reply = vehicleService.addVehicleFuelData(object, userId);
		if(reply!=null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
