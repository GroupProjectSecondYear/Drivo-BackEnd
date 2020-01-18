package com.gp.learners.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.PaperQuestion;
import com.gp.learners.entities.Pdf;
import com.gp.learners.entities.mapObject.PaperAnswerMap;
import com.gp.learners.repositories.PaperRepository;
import com.gp.learners.service.PaperQuestionService;
import com.gp.learners.service.PaperService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class PaperQuestionController {

	@Autowired
	PaperQuestionService paperquestionService;

	// get questions of a paper
	@GetMapping("/paperquestions/{paperId}")
	public List<PaperQuestion> getPaperList(@PathVariable("paperId") Integer paperId) {
		System.out.println("PQ controller");
		List<PaperQuestion> answerList = paperquestionService.getPaperQuestionList(paperId);
		if (answerList != null)
			return answerList;
		else return null;
	}

	// update questions of a paper
	@PostMapping("paperAnswers/update") // save paper and answers
	public Integer updatePaper(@Valid @RequestBody PaperAnswerMap paperAnswerMap) {
		System.out.println("In Answer Update Controller");

		Integer val = paperquestionService.updatePaperAnswers(paperAnswerMap.getPaper(), paperAnswerMap.getAnswers());
		// Integer val = 0;
		if (val != null) {
			return val;
		}
		return 0;
	}

	// update Answers
//			@PostMapping("/paperAnswers/update")
//			public Integer updatePdf(@RequestBody Integer[] answers){
////				System.out.println("In Pdf Update Controller");
////				Pdf pdf=pdfService.updatePdf(object);
////				if(pdf.getPdfId() != null) {
////					return ResponseEntity.ok(pdf);
////				}
////				return ResponseEntity.notFound().build();
//				return 1;
//			}
//	

}
