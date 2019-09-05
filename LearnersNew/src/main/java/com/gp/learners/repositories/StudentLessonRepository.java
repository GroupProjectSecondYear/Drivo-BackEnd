package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Lesson;
import com.gp.learners.entities.StudentLesson;

public interface StudentLessonRepository extends JpaRepository<StudentLesson,Integer>{
	
	@Query(value="select * from student_lesson s where s.lesson_id = :lessonId limit 1",nativeQuery=true)
	public StudentLesson isExistByLessonId(@Param("lessonId")Lesson lessonId);
}
