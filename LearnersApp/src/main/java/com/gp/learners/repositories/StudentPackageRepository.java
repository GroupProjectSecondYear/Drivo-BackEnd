package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentPackage;

public interface StudentPackageRepository extends JpaRepository<StudentPackage, Integer>{
	
	//select * from studentpackage where studentId="?"
//	@Query("from studentPackage where studentId  =  :studentId")
//	List<StudentPackage> getStudentDetailsId(@Param("studentId") Integer StudentId);
	
//	@Query("from StudentPackage where studentId = :studentId")
//	public List<StudentPackage> getStudentId(@Param("studentId")Student studentId);
	
	
	@Query(value="select package_id from student_package u WHERE u.student_id = :studentId ",nativeQuery=true)
	public List<Integer> findByStudentId(@Param("studentId")Student studentId);
	
	//get Student following package details
	@Query(value="select * from student_package u WHERE u.student_id = :studentId ",nativeQuery=true)
	public List<StudentPackage> packageListfindByStudentId(@Param("studentId")Student studentId);
		
	
	@Query(value="select * from student_package u where u.package_id = :packageId and u.student_id = :studentId",nativeQuery=true)
	public StudentPackage findByStudentIdAndPackageId(@Param("studentId")Student studentId,@Param("packageId")com.gp.learners.entities.Package packageId);
	
	
//	@Modifying
//	@Query(value="delete from student_package u where u.package_id = :packageId and u.student_id = :studentId",nativeQuery=true)
//	public void deletePackageByStudentIdAndPackageId(@Param("studentId")Student studentId,@Param("packageId")com.gp.learners.entities.Package packageId);
	
	@Query(value="select * from student_package u WHERE u.student_package_id = :studentPackageId ",nativeQuery=true)
	public StudentPackage findByStudentPackageId(@Param("studentPackageId")Integer studentPackageId);
	
	@Query(value="select count(student_package_id) from student_package p ,student s ,user u where p.package_id = :packageId and p.transmission = :transmission and "
				+ "p.student_id = s.student_id and s.user_id = u.user_id and u.status=1 ",nativeQuery=true)
	public Integer TotalStufindByPackgeId(@Param("packageId")Package packageId, @Param("transmission") Integer transmission);
}
