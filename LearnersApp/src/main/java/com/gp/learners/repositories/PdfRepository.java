package com.gp.learners.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Pdf;


public interface PdfRepository extends JpaRepository<Pdf, Integer>{

	//get pdf by id
	@Query("from Pdf where pdfId = :pdfId")
	public Pdf getPdfById(@Param("pdfId")Integer pdfId);
}

