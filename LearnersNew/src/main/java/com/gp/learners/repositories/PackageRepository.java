package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gp.learners.entities.Package;

public interface PackageRepository extends JpaRepository<com.gp.learners.entities.Package, Integer>{
	
	public com.gp.learners.entities.Package findByPackageId(Integer packageId);
	
	@Query("from Package p where p.status = :status")
	public List<Package> findPackages(Integer status);
}
