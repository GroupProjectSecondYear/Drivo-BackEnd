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
import com.gp.learners.entities.StudentPaper;
import com.gp.learners.repositories.PaperRepository;
import com.gp.learners.service.PaperService;
import com.gp.learners.service.StudentPaperService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class StudentPaperController {

	@Autowired
	StudentPaperService studentPaperService;

	// add Student paper
	@PostMapping("/studentPaper/add/{answers}") // save paper and answers
	public ResponseEntity<StudentPaper> addPaper(@Valid @RequestBody StudentPaper paperAnswer,@PathVariable("answers") Integer[] answers) {
		// System.out.println("stuId:"+stuId+" object:"+object);
		System.out.println("In Student papaer Answer add controller");
		StudentPaper studnetPaper1 = studentPaperService.addStudentPaper(paperAnswer, answers);
		// System.out.println(paper1.getPaperId()+"paperId9f"); 
		// System.out.println(answers[0][0]);
		if (studnetPaper1.getPaperId() != null) { // paper is saved

			return ResponseEntity.ok(studnetPaper1);
		}
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/paperMarks")
	public List<StudentPaper> getPaperMarksList(){
		return studentPaperService.getPaperMarksList();
	}
//
//	@Autowired
//	PaperService paperService;
//
//	@Autowired
//	PaperRepository paperRepository;
//
//	@GetMapping("/papers")
//	public List<Paper> getPaperList(){
//		return paperService.getPaperList();
//	}
//
//	@GetMapping("paper/{paperId}")
//	public ResponseEntity<Paper> getPaper(@PathVariable("paperId") Integer paperId) {
//		Paper paper=paperService.getPaperByID(paperId);
//		if(paper.getPaperId() != null) {
//			return ResponseEntity.ok(paper);
//		}
//		return ResponseEntity.noContent().build();
//	}
//

//
//		//delete Paper
//		@DeleteMapping("paper/delete/{paperId}")
//		public ResponseEntity<Void> deletePaper(@PathVariable("paperId") Integer paperId){
//			System.out.println("Delete Paper");
//			String reply=paperService.deletePaper(paperId);
//			if(reply.equals("success")) {
//				return ResponseEntity.noContent().build();
//			}
//			return ResponseEntity.notFound().build();
//		}
//		
//		//update student Details
//		@PutMapping("paper/update")
//		public ResponseEntity<Paper> updatePaper(@Valid @RequestBody Paper object){
//			System.out.println("In Paper Update Controller");
//			Paper paper=paperService.updatePaper(object);
//			if(paper.getPaperId() != null) {
//				return ResponseEntity.ok(paper);
//			}
//			return ResponseEntity.notFound().build();
//		}

}