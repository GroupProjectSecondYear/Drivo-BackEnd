package com.gp.learners.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Student;
import com.gp.learners.entities.User;
import com.gp.learners.entities.mapObject.StudentTrialMap;

public interface StudentRepository extends JpaRepository<Student, Integer>{

	//get All Active Student
	@Query(value="select * from student u ,user v where u.user_id=v.user_id and v.status = :status order by v.first_name ASC",nativeQuery=true)
	public List<Student> getStudent(@Param("status") Integer status);
	
	//getstudent by id
	@Query("from Student where studentId = :studentId")
	public Student getStudentId(@Param("studentId")Integer studentId);
	
	//find student by Id
	@Query("from Student where studentId = :studentId")
	public Student findByStudentId(@Param("studentId")Integer studentId);
	
//	@Query(value="select * from student u where status",nativeQuery=true)
//	public List<String> findActiveStudents();
	
	//findStudent by UserId
	@Query(value="select student_id from student u WHERE u.user_id = :userId ",nativeQuery=true)
	public Integer findByUserId(@Param("userId")User user);
	
	//getTrialStudentList
	@Query(value="select * from student s,user u where s.user_id=u.user_id and s.trial_date = :trialDate and u.status=1",nativeQuery=true)
	public List<Student> findByTrialDate(@Param("trialDate") LocalDate trialDate);
	
	//getExamStudentList
	@Query(value="select * from student s,user u where s.user_id=u.user_id and s.exam_date = :examDate and u.status=1",nativeQuery=true)
	public List<Student> findByExamDate(@Param("examDate") LocalDate examDate);
	
	@Query(value="select * from student u where u.nic = :nic",nativeQuery=true)
	public Student findByNic(@Param("nic") String nic);
	
	@Query(value="select * from student s,user u where s.user_id=u.user_id and  s.trial_date < :currentDate and u.status=1 ",nativeQuery=true)
	public List<Student> findByDate(@Param("currentDate") LocalDate currentDate);
	
	@Query(value="select * from student s,user u where u.user_id = s.user_id AND " + 
			 "(WEEK(s.trial_date) = WEEK(:currentDate) or WEEK(s.trial_date) = WEEK(:currentDate)+1 ) " + 
			 "AND YEAR(s.trial_date) = YEAR( :currentDate ) AND " + 
			 "s.trial_date>=:currentDate AND u.status=1 order by s.trial_date ASC",nativeQuery=true)
	public List<Student> findTrialExaminationStudentByWeek(@Param("currentDate") LocalDate currentDate);
	
}
