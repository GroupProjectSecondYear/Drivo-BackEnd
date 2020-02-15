package com.gp.learners.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.StudentPaper;


public interface StudentPaperRepository extends JpaRepository<StudentPaper, Integer>{

//	//get paper by id
//	@Query("from Paper where paperId = :paperId")
//	public Paper getPaperById(@Param("paperId")Integer paperId);



}