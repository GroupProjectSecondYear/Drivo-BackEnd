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
	@Query(value="select * from paper_question where paper_id=:paperId order by question_no ASC", nativeQuery = true)
	public List<PaperQuestion> getAnswersOfaPaper(@Param("paperId") Integer paperId);
	
	// delete particular question using question no 
	@Modifying
	@Transactional
	@Query(value="delete from paper_question where paper_id=:paperId and question_no=:questionNo", nativeQuery = true)
	public Integer deleteQuestionByQuestionNo(@Param("paperId") Integer paperId,@Param("questionNo") Integer questionNo);

	//add characters to answers
	@Modifying
	@Transactional
	@Query(value="UPDATE paper_question SET answer = concat(answer,:val) WHERE paper_id=:paperId and question_no=:questionNo", nativeQuery = true)
	public Integer addExtraAnswers(@Param("paperId") Integer paperId,@Param("questionNo") Integer questionNo,@Param("val") String val);

}