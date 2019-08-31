package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson,Integer>{

}
