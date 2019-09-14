package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
