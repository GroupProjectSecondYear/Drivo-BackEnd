package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.PaperQuestion;

public interface PaperQuestionRepository extends JpaRepository<PaperQuestion, Integer> {

	// delete questions of a paper
	@Modifying
	@Transactional
	@Query(value="delete from paper_question where paper_id=:paperId", nativeQuery = true)
	public Integer deleteQuestionsOfaPaper(@Param("paperId") Integer paperId);

	//send questions-answers set of a paper 
	@Query(value="select * from paper_question where paper_id=:paperId", nativeQuery = true)
	public List<PaperQuestion> getAnswersOfaPaper(@Param("paperId") Integer paperId);

	
}