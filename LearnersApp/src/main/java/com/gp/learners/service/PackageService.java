package com.gp.learners.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.entities.VehicleCategory;
import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;
import com.gp.learners.repositories.VehicleCategoryRepository;
import com.gp.learners.repositories.VehicleRepository;

@Service
public class PackageService {
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	StudentPackageRepository studentPackageRepository;
	
	@Autowired
	VehicleCategoryRepository vehicleCategoryRepository;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	
	public Integer getNumStudentPackage(Integer packageId,Integer transmissionType) {
		Integer numStu=-1;
		if(packageRepository.existsById(packageId)) {
			numStu=studentPackageRepository.TotalStufindByPackgeId(packageRepository.findByPackageId(packageId),transmissionType);
		}
		return numStu;
	}
	
	public List<VehicleCategory> getVehicleCategoryList() {
		List<VehicleCategory> vehicleCategoryList = vehicleCategoryRepository.findAll();
		return vehicleCategoryList;
	}
	
	public Integer getVehcleCategoryTransmission(Integer vehicleCategoryId) {
		if(vehicleCategoryRepository.existsById(vehicleCategoryId)) {
			Boolean transmissionManual = vehicleRepository.findVehicleTransmission(vehicleCategoryId,1);
			Boolean transmissionAuto = vehicleRepository.findVehicleTransmission(vehicleCategoryId,2);
			
			 
			if(transmissionManual!=null && transmissionAuto!=null) {
				return 3;
			}else if(transmissionManual!=null) {
				return 1;
			}else if(transmissionAuto!=null) {
				return 2;
			}else {
				return 0;
			}
		}
		return null;
	}
}
