package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.DeletePaymentOfStudent;
import com.gp.learners.entities.Package;

public interface DeletePaymentOfStudentRepository extends JpaRepository<DeletePaymentOfStudent,Integer>{
	
	@Query(value="select sum(u.amount) from delete_payment_of_student u where year(u.date) = :year and month(u.date) = :month ",nativeQuery=true)
	public Double findPaymentByMonthAndYear(@Param("year") Integer year,@Param("month") Integer month);
	
	@Query("select sum(c.amount) from DeletePaymentOfStudent c where year(c.date) = :year and month(c.date) = :month and "
			+ "c.packageId = :packageData")
	public Double findPaymentByPackageIdAndYearAndMonth(@Param("packageData") Package packageData,@Param("year") Integer year,@Param("month") Integer month);
}
