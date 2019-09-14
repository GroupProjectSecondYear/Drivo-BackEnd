package com.gp.learners.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.learners.repositories.PackageRepository;
import com.gp.learners.repositories.StudentPackageRepository;
import com.gp.learners.repositories.StudentRepository;

@Service
public class PackageService {
	
	@Autowired
	PackageRepository packageRepository;
	
	@Autowired
	StudentPackageRepository studentPackageRepository;
	
	
	public Integer getNumStudentPackage(Integer packageId,Integer transmissionType) {
		Integer numStu=-1;
		if(packageRepository.existsById(packageId)) {
			numStu=studentPackageRepository.TotalStufindByPackgeId(packageRepository.findByPackageId(packageId),transmissionType);
		}
		return numStu;
	}
}
