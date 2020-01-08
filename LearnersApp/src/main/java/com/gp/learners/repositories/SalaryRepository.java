package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Salary;
import com.gp.learners.entities.Staff;

public interface SalaryRepository extends JpaRepository<Salary, Integer>{
	
	@Query(value="select * from salary s,staff e,user u where "
				+"s.staff_id=e.staff_id and "
				+ "e.user_id=u.user_id and "
				+ "u.status = 1 and "
				+ "s.month = :month order by s.complete asc,u.role asc,s.total_payment desc"
				,nativeQuery=true)
	public List<Salary> findSalaryListByMonth(@Param("month")Integer month);
	
	@Query(value="select * from salary where staff_id = :staffId and month = :month",nativeQuery=true)
	public Salary findByStaffIdAndMonth(@Param("staffId") Staff staffId,@Param("month") Integer month);

	//check not comepleted salary payments 
	@Query(value="select month FROM salary where staff_id=:staffId and complete=0;",nativeQuery=true)
	public List<String> checkInstructorSalaryPayments(@Param("staffId") Integer staffId);


	@Query(value="select month FROM salary where staff_id=1;",nativeQuery=true)
	public List<String> checkInstructorSalaryDetails(@Param("staffId") Integer staffId);

	
}
