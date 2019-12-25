package com.gp.learners.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.FuelPayment;
import com.gp.learners.entities.InsurancePayment;
import com.gp.learners.entities.Vehicle;
import com.gp.learners.repositories.VehicleRepository;
import com.gp.learners.service.VehicleService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;

	


	@GetMapping("/vehicles")
	public List<Vehicle> getVehicleList() {
		System.out.println("In Vehicle Controller");
		return vehicleService.getVehicleList();

	}

	// get Specific vehicle data
	@GetMapping("vehicles/{vehicleId}")
	public ResponseEntity<Vehicle> getVehicle(@PathVariable("vehicleId") Integer vehicleId) {
		System.out.println("In vehicle Controller VmoreDetails");
		Vehicle vehicle = (Vehicle) vehicleService.getVehicleList(vehicleId);
		if (vehicle.getVehicleId() != null) {
			return ResponseEntity.ok(vehicle);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	// save vehicle
	@PostMapping("/vehicle/add")

	public Vehicle saveVehicle(@RequestBody Vehicle vehicle) {
		System.out.println("In Vehicle controller Adding method");
		return vehicleRepository.save(vehicle);
	}

	// delete Video
	@DeleteMapping("vehicle/{vehicleId}")
	public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleId") Integer vehicleId) {
		System.out.println("In vehicleId controller delete method");

		String reply = vehicleService.deleteVehicle(vehicleId);
		if (reply.equals("success")) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	// update vehicle Details
	@PutMapping("vehicle/update")
	public ResponseEntity<Vehicle> updateVehicle(@Valid @RequestBody Vehicle object) {
		Vehicle vehicle = vehicleService.updateVehicle(object);
		if (vehicle.getVehicleId() != null) {
			return ResponseEntity.ok(vehicle);
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
	
	@GetMapping("vehicle/fuel")
	public ResponseEntity<List<FuelPayment>> getVehicleFuelData(){
		return ResponseEntity.ok(vehicleService.getVehicleFuelData());
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
