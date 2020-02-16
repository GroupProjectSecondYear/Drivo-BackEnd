package com.gp.learners.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult,Integer>{
	
	@Query(value="select sum(written_exam) from exam_result u WHERE u.date BETWEEN :start AND :end AND year(date) = :year",nativeQuery=true)
	Double findWrittenExamResultSum(@Param("start")Date start,@Param("end")Date end,@Param("year") Integer year);
	
	@Query(value="select sum(written_exam_fail) from exam_result u WHERE u.date BETWEEN :start AND :end AND year(date) = :year",nativeQuery=true)
	Double findWrittenExamResultFailSum(@Param("start")Date start,@Param("end")Date end,@Param("year") Integer year);
	
	@Query(value="select sum(trial_exam) from exam_result u WHERE u.date BETWEEN :start AND :end AND year(date) = :year",nativeQuery=true)
	Double findTrialExamResultSum(@Param("start")Date start,@Param("end")Date end,@Param("year") Integer year);
	
	@Query(value="select sum(trial_exam_fail) from exam_result u WHERE u.date BETWEEN :start AND :end AND year(date) = :year",nativeQuery=true)
	Double findTrialExamResultFailSum(@Param("start")Date start,@Param("end")Date end,@Param("year") Integer year);
	
	@Query(value="select *  from exam_result u WHERE u.date = :date",nativeQuery=true)
	ExamResult findByDate(@Param("date")LocalDate date);
}
