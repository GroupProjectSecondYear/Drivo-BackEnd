package com.gp.learners.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.repositories.PackageRepository;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class PackageController {
	
	@Autowired
	PackageRepository PackageRepository;
	
	@GetMapping("/packages")
	public List<com.gp.learners.entities.Package> getPackages(){
		return PackageRepository.findAll();
	}
}
