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
import org.springframework.web.bind.annotation.RestController;


import com.gp.learners.entities.Paper;
import com.gp.learners.entities.PaperQuestion;
import com.gp.learners.repositories.PaperRepository;
import com.gp.learners.service.PaperQuestionService;
import com.gp.learners.service.PaperService;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*",maxAge=3600)
public class PaperQuestionController {

	@Autowired
	PaperQuestionService paperquestionService;

	@GetMapping("/paperquestions/{paperId}")
	public List<PaperQuestion> getPaperList(@PathVariable("paperId") Integer paperId){
		System.out.println("PQ controller");
		return paperquestionService.getPaperQuestionList(paperId);
	}

}