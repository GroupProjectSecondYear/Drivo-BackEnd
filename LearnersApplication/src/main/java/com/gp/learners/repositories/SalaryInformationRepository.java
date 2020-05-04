package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Salary;
import com.gp.learners.entities.SalaryInformation;

public interface SalaryInformationRepository extends JpaRepository<SalaryInformation, Integer>{
	
	@Query(value="select * from salary_information s where s.salary_information_id = :salaryInformationId",nativeQuery=true)
	public SalaryInformation findBySalaryInformationId(@Param("salaryInformationId")Integer salaryInformationId);
	
//	@Query(value="select * from salary_information s where s.staff_type = :staffType",nativeQuery=true)
//	public SalaryInformation findByStaffType(@Param("staffType") Integer staffType);
	
	@Query(value="select * from salary_information where " + 
				 "(" + 
				 "(year(update_date) = :year and apply_month < :month ) or " + 
				 "(year(update_date) < :year) "+
				  ") " + 
				 "and staff_type = :staffType " + 
				 "order by update_date DESC limit 1",nativeQuery=true)
	public SalaryInformation findRelevantRecord(@Param("year") Integer year,@Param("month") Integer month,@Param("staffType")Integer staffType);
	
	@Query(value="select *from salary_information where year(update_date) = :currentYear and apply_month = :applyMonth and staff_type = :staffType ",nativeQuery=true)
	public SalaryInformation findByYearAndMonth(@Param("currentYear") Integer currentYear,@Param("applyMonth")Integer applyMonth,@Param("staffType") Integer staffType);


	@Query(value="select * from salary_information  where staff_type = :staffType order by update_date DESC limit 1",nativeQuery=true)
	public SalaryInformation findLastUpdateRecordByStaffType(Integer staffType);
}
