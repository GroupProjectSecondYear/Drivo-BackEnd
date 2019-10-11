package com.gp.learners.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.Package;
import com.gp.learners.entities.Student;
import com.gp.learners.entities.StudentLesson;

import net.bytebuddy.asm.Advice.Local;

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
				 "( WEEK(date) = WEEK( :currentDate )   OR   WEEK(date) = WEEK( :currentDate ) - 1 ) AND " + 
				 "YEAR(date) = YEAR( :currentDate )  AND " + 
				 "lesson_id = :lessonId ",nativeQuery=true)
	public Integer findStudentAttendanceForLesson(@Param("lessonId") Integer lessonId,@Param("currentDate") LocalDate currentDate);
	
	@Query(value="SELECT count(*) FROM student_lesson WHERE  " + 
			 	 "WEEK   (date) = WEEK( current_date ) - :weekNum AND YEAR(date) = YEAR( current_date ) AND " +
			 	 "lesson_id = :lessonId ",nativeQuery=true)
	public Integer findStudentAttendanceForLesson12WeeksPast(@Param("lessonId") Integer lessonId,@Param("weekNum") Integer weekNum);
	
	@Query(value="SELECT count(*) FROM student_lesson WHERE  " + 
		 	 "WEEK   (date) = WEEK( current_date ) + :weekNum AND YEAR(date) = YEAR( current_date ) AND " +
		 	 "lesson_id = :lessonId ",nativeQuery=true)
	public Integer findStudentAttendanceForLesson12WeeksFuture(@Param("lessonId") Integer lessonId,@Param("weekNum") Integer weekNum);
	
	@Query(value="select * from student_lesson s,lesson l where " + 
				 "s.lesson_id = l.lesson_id and s.lesson_id = :lessonId and l.instructor_id = :instructorId and " + 
				 "(WEEK   (s.date) = WEEK( current_date ) AND YEAR(s.date) = YEAR( current_date ))",nativeQuery=true)
	public List<StudentLesson> findByLessonIdAndInstructorId(@Param("lessonId") Integer lessonId,@Param("instructorId") Integer instructorId);
	
	@Query(value="select s.date from student_lesson s,lesson l where " + 
			 "s.lesson_id = l.lesson_id and s.lesson_id = :lessonId and l.instructor_id = :instructorId and " + 
			 "(WEEK   (s.date) = WEEK( current_date ) AND YEAR(s.date) = YEAR( current_date )) LIMIT 1 ",nativeQuery=true)
	public LocalDate getDateByLessonIdAndInstructorId(@Param("lessonId") Integer lessonId,@Param("instructorId") Integer instructorId);
	
	@Query(value="select * from student_lesson s  where s.student_lesson_id = :studentLessonId ",nativeQuery=true)
	public StudentLesson findByStudentLessonId(@Param("studentLessonId")Integer studentLessonId);
	
	@Query(value="select * from student_lesson s,lesson l,time_slot t  where  " + 
				 "l.lesson_id = s.lesson_id and t.time_slot_id=l.time_slot_id and " + 
				 "(t.start_time between :startTime and :finishTime OR " + 
				 " t.finish_time between :startTime and :finishTime) and " + 
				 " s.student_id = :studentId and s.date = :date LIMIT 1 ",nativeQuery=true)
	public StudentLesson findByStudentIdAndDateANDTime(@Param("studentId") Integer studentId,@Param("date") LocalDate date,@Param("startTime") LocalTime startTime,@Param("finishTime") LocalTime finishTime);
	
	
	@Query(value="select count(*) from student_lesson s,lesson l,time_slot t where " + 
				 "s.lesson_id = l.lesson_id and t.time_slot_id = l.time_slot_id and "+
				 "l.package_id = :packageId and " + 
				 "s.student_id = :studentId and s.complete = :state and " +
				 "( s.date < ( current_date )  or " + 
				 "	( " + 
				 " 	CASE " + 
				 "		when s.date = ( current_date ) THEN t.start_time < now() " + 
				 " 	END " + 
				 "	)" + 
				 ") ;"
				 ,nativeQuery=true)
	public Integer getStudentLessonCountByStudentIdAndPackageId(@Param("studentId") Integer studentId,@Param("packageId") Integer packageId,@Param("state") Integer state);
	
	@Query(value="select count(*) from student_lesson s,lesson l,time_slot t where " + 
			 "s.lesson_id = l.lesson_id and t.time_slot_id = l.time_slot_id and "+
			 "l.package_id = :packageId and " + 
			 "s.student_id = :studentId and  " +
			 "( s.date > ( current_date )  or " + 
			 "	( " + 
			 " 	CASE " + 
			 "		when s.date = ( current_date ) THEN t.start_time > now() " + 
			 " 	END " + 
			 "	)" + 
			 ") ;"
			 ,nativeQuery=true)
	public Integer getStudentLessonBookFutureCountByStudentIdAndPackageId(@Param("studentId") Integer studentId,@Param("packageId") Integer packageId);
	
	@Query(value="select * from student_lesson l where l.lesson_id = :lessonId and l.date > (current_date) ",nativeQuery=true)
	public List<StudentLesson> findNotificationStudentListByLessonId(@Param("lessonId") Lesson lessonId);
	
	
	public void deleteByStudentId(Student studentId);
}
