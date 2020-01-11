package com.gp.learners.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.Question;


public interface QuestionRepository extends JpaRepository<Question, Integer>{


}