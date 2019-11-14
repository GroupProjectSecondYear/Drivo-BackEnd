package com.gp.learners.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.VehicleCategory;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.service.PackageService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class PackageController {
	
	@Autowired
	PackageRepository PackageRepository;
	
	@Autowired
	PackageService packageService;
	
	@GetMapping("/packages")
	public List<com.gp.learners.entities.Package> getPackages(){
		return PackageRepository.findAll();
	}
	
	//get total number of student(active) which are follwing relevant package 
	@GetMapping("package/student/{packageId}/{transmissionType}")
	public ResponseEntity<Integer> getNumStudentPackage(@PathVariable("packageId") Integer packageId,@PathVariable("transmissionType") Integer transmissionType){
		Integer reply=packageService.getNumStudentPackage(packageId,transmissionType);
		if(reply != -1) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("package/vehicle/category")
	public ResponseEntity<List<VehicleCategory>> getVehicleCategoryList(){
		List<VehicleCategory> vehicleCategoryList = packageService.getVehicleCategoryList();
		if(vehicleCategoryList!=null) {
			return ResponseEntity.ok(vehicleCategoryList);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("package/vehicle/transmission/{vehicleCategoryId}")
	public ResponseEntity<Integer> getVehcleCategoryTransmission(@PathVariable("vehicleCategoryId") Integer vehicleCategoryId){
		Integer reply = packageService.getVehcleCategoryTransmission(vehicleCategoryId);
		if(reply!=null) {
			return ResponseEntity.ok(reply);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("package/register")
	public ResponseEntity<com.gp.learners.entities.Package> registerPackage(@Validated @RequestBody com.gp.learners.entities.Package packageData) {
		System.out.println(packageData);
		com.gp.learners.entities.Package object = packageService.registerPackage(packageData);
		if(object!=null) {
			return ResponseEntity.ok(object);
		}
		return ResponseEntity.notFound().build();
	}
}
