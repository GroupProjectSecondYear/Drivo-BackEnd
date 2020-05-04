package com.gp.learners.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.PaperQuestion;
import com.gp.learners.entities.StudentPaper;
import com.gp.learners.repositories.PaperRepository;
import com.gp.learners.repositories.StudentPaperRepository;
import com.gp.learners.repositories.PaperQuestionRepository;

@Service
public class StudentPaperService {

	@Autowired
	StudentPaperRepository studentpaperRepository;

	@Autowired
	PaperQuestionRepository paperQuestionRepository;

	// Add Students answers for a paper
	public StudentPaper addStudentPaper(StudentPaper studentPaper, Integer[] answers) {

		System.out.println("Studnet Papaer Servide add Method");

		// get saved answers of the paper
		List<PaperQuestion> paperQuestionAnswers = paperQuestionRepository.getAnswersOfaPaper(studentPaper.getPaperId().getPaperId());

		// mark answers
		String ans = answers[0].toString();
		int val = 0;
		int marks = 0;
		for (int n = 1; n < answers.length; n++) {
			ans += answers[n].toString();
			if (n % (answers.length) == 0) {
				val++;
				if (paperQuestionAnswers.get(val).getAnswer() == ans) {
					marks++;
				}

			}
		}
		// set marks of the student
		int finalMarks=(marks/studentPaper.getPaperId().getNo_of_questions())*100;
		//System.out.println();
		studentPaper.setMarks(50);

		// save student paper details
		StudentPaper result = studentpaperRepository.save(studentPaper);
		System.out.println("End Saving papaer");

		return result;

	}
	
	// getPaperList
		public List<StudentPaper> getPaperMarksList() {
			System.out.println("IN paperrepo");
			List<StudentPaper> paperList = studentpaperRepository.findAll();
			// Paper paper1=paperList.get(0);
			return paperList;
		}

//
//	@Autowired
//	PaperQuestionRepository questionRepository;
//
//	@Autowired
//	S3Service s3Service;
//
//	@Value("${aws.s3.bucket.learning_material_paper}")
//	private String bucketName;

//	// getPaperList
//	public List<Paper> getPaperList() {
//		System.out.println("IN paperrepo");
//		List<Paper> paperList = paperRepository.findAll();
//		// Paper paper1=paperList.get(0);
//		return paperList;
//	}
//
//	public Paper getPaperByID(Integer paperId) {
//		if (paperId != null) {
//			if (paperRepository.existsById(paperId)) {
//				return paperRepository.getPaperById(paperId);
//			}
//		}
//		return new Paper();
//	}
//

//
//	// delete Paper
//	public String deletePaper(Integer paperId) {
//		System.out.println("Delete Paper Serv");
//		if (paperId != null) {
//			if (paperRepository.existsById(paperId)) {
//				String keyName = paperId + ".pdf";
//				// delete paper from s3 bucket
//				s3Service.deleteFile(bucketName, keyName);
//
//				System.out.println(paperId);
//				questionRepository.deleteQuestionsOfaPaper(paperId); // delete all questions of the paper
//				paperRepository.deleteById(paperId);
//				return "success";
//			}
//		}
//		return "error";
//	}
//
//	// update Paper
//	public Paper updatePaper(Paper paper) {
//		if (paperRepository.existsById(paper.getPaperId())) {
//			Paper savedPaper = paperRepository.getPaperById(paper.getPaperId());
//
//			// if no of questions updating, change question table
//			if (paper.getNo_of_questions() != savedPaper.getNo_of_questions()) { 
//
//				//If no of questions large than current value should save empty answers in db
//				if (paper.getNo_of_questions() > savedPaper.getNo_of_questions()) {
//					String blank = "0";
//					for (int j = 1; j < paper.getNo_of_answers(); j++) { // zeros as answer
//						blank += "0";
//					}
//
//					for (int i = savedPaper.getNo_of_questions() + 1; i < paper.getNo_of_questions() + 1; i++) {
//						PaperQuestion quest = new PaperQuestion(0, paper, i, blank); // saving as new answers
//						PaperQuestion replyQ = questionRepository.save(quest);
//						
//						if (replyQ == null) { // if saving not successfull delete saved answers
//							for (int k = savedPaper.getNo_of_questions() + 1; k < i + 1; k++) {
//								questionRepository.deleteQuestionByQuestionNo(paper.getPaperId(), k);
//								System.out.println("Deleting Quest");
//								return new Paper();
//							}
//						}
//					}
//				}
//				
//				//If no of questions less than current value should delete extra answers
//				if (paper.getNo_of_questions() < savedPaper.getNo_of_questions()) {
//					for (int i = paper.getNo_of_questions() + 1; i < savedPaper.getNo_of_questions() + 1; i++) {
//						System.out.println("Deleting Ans");
//						questionRepository.deleteQuestionByQuestionNo(paper.getPaperId(), i);
//						//return new Paper();
//
//					}
//				}
//			}
//
//			// if no of answers updating, change question table
//			if (paper.getNo_of_answers() != savedPaper.getNo_of_answers()) { 
//				System.out.println(paper.getNo_of_answers()+"No Of Answers");
//				//new no of answers is large than current number
//				if (paper.getNo_of_answers() > savedPaper.getNo_of_answers()) {
//					//generate extra answer part
//					String val="0";
//					for(int i=savedPaper.getNo_of_answers()+1;i<paper.getNo_of_answers();i++) {
//						val+="0";
//					}
//				    System.out.println("ZEros"+val);
//					for(int i=1;i<=paper.getNo_of_answers()+1;i++) {
//						questionRepository. addExtraAnswers(paper.getPaperId(), i,val);
//					}
//					//questionRepository. addExtraAnswers(paper.getPaperId(), paper.getNo_of_questions(),val);//bcs of a miss working behaviour
//				
//				}
//				//new no of answers is small than current number
//				if (paper.getNo_of_answers() < savedPaper.getNo_of_answers()) {
//				
//					//remove extra part from saved answers
//					//
//					//
//					//
//					//
//				
//				}
//			}
//			return paperRepository.save(paper);
//		}
//
//		return new Paper(); ///////// ???
//	}
//
//	public String uploadPaper(MultipartFile file, Integer paperId) {
//		System.out.println("Paper UPLOADING");
//		if (paperRepository.existsById(paperId)) {
//			String keyName = paperId + ".paper";
//			if (keyName != null) {
//				s3Service.uploadFile(keyName, file, bucketName);
//				Paper paper = paperRepository.getPaperById(paperId);
//				// user.setProfileImage(1);
//				// userRepository.save(user);
//				System.out.println("Paper UPLOADING success");
//				return "success";
//			}
//		}
//		return null;
//	}
//
//	public ByteArrayOutputStream downloadPaper(Integer paperId) {
//		if (paperRepository.existsById(paperId)) {
//			System.out.println("PDF dwnlding Byte Array Method");
//			String keyName = paperId + ".paper";
//
//			if (paperId != null) {
//				Paper paper = paperRepository.getPaperById(paperId);
//				try {
//					System.out.println("PDF dwnLOADING Byte Array Method try chtch");
//					/*
//					 * if (paper.getProfileImage() == 1) { return s3Service.downloadFile(keyName,
//					 * bucketName); } else {
//					 */
//					return s3Service.downloadFile(keyName, bucketName);
//					/* } */
//				} catch (Exception e) {
//					System.out.println("There is a problem in s3 bucket");
//				}
//
//			}
//		}
//		return null;
//	}

}