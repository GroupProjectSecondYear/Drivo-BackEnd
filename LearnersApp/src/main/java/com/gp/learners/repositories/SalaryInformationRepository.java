package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.SalaryInformation;

public interface SalaryInformationRepository extends JpaRepository<SalaryInformation, Integer>{
	
	@Query(value="select * from salary_information s where s.salary_information_id = :salaryInformationId",nativeQuery=true)
	public SalaryInformation findBySalaryInformationId(@Param("salaryInformationId")Integer salaryInformationId);
	
	@Query(value="select * from salary_information s where s.staff_type = :staffType",nativeQuery=true)
	public SalaryInformation findByStaffType(@Param("staffType") Integer staffType);
}
