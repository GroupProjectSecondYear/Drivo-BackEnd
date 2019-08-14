package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<com.gp.learners.entities.Package, Integer>{
	
	public com.gp.learners.entities.Package findByPackageId(Integer packageId);
}
