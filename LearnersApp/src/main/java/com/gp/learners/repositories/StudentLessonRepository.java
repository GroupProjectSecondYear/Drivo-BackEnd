package com.gp.learners.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentLesson;

public interface StudentLessonRepository extends JpaRepository<StudentLesson,Integer>{
	
	@Query(value="select * from student_lesson s where s.lesson_id = :lessonId limit 1",nativeQuery=true)
	public StudentLesson isExistByLessonId(@Param("lessonId")Lesson lessonId);
	
	@Query(value="select count(student_lesson_id) from student_lesson l where l.lesson_id = :lessonId and l.date = :date",nativeQuery=true)
	public Integer findLessonBookCount(@Param("lessonId")Lesson lessonId,@Param("date")LocalDate date);
	
	@Query(value="select count(*) from student_lesson s,lesson l where s.lesson_id = l.lesson_id and l.package_id = :packageId and s.student_id = :studentId",nativeQuery=true)
	public Integer findAlreadyBookLesson(@Param("studentId")Student studentId,@Param("packageId")Package packageId);
	
	@Query(value="select * from student_lesson l where l.lesson_id = :lessonId and l.student_id = :studentId and l.date = :date ",nativeQuery=true)
	public StudentLesson findStudentIdPackageIdDate(@Param("lessonId")Lesson lessonId,@Param("studentId")Student studentId,@Param("date")LocalDate date);
	
	@Query(value="select * from student_lesson s,lesson l where s.lesson_id = l.lesson_id and s.student_id = :studentId and l.package_id = :packageId ORDER BY s.date ASC ",nativeQuery=true)
	public List<StudentLesson> findByStudentIdAndPackageId(@Param("studentId") Student studentId,@Param("packageId") Package packageId);
	
	@Query(value="SELECT count(*) FROM student_lesson WHERE  " + 
				 "WEEK   (date) = WEEK( current_date ) - 1 AND YEAR(date) = YEAR( current_date ) OR  " + 
				 "WEEK   (date) = WEEK( current_date ) - 2 AND YEAR(date) = YEAR( current_date ) AND " +
				 "lesson_id = :lessonId ",nativeQuery=true)
	public Integer findStudentAttendanceForLesson(@Param("lessonId") Integer lessonId);
}
