package com.gp.learners.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.PaperQuestion;
import com.gp.learners.entities.StudentLesson;
import com.gp.learners.repositories.PaperRepository;
import com.gp.learners.repositories.PaperQuestionRepository;

@Service
public class PaperQuestionService {

	@Autowired
	PaperQuestionRepository paperQuestionRepository;

	@Autowired
	PaperRepository paperRepository;

	// getPaperList
	public List<PaperQuestion> getPaperQuestionList(Integer paperId) {
		System.out.println("IN paperQuestrepo" + paperId);
		List<PaperQuestion> paperQuestionList = paperQuestionRepository.getAnswersOfaPaper(paperId);
		// Paper paper1=paperList.get(0);
		System.out.println("PaperQuest1" + paperQuestionList.get(0).getAnswer());
		return paperQuestionList;
	}

	// update answers of a paper
	public Integer updatePaperAnswers(Paper paper, String answers) {
		System.out.println(paper.getPaperId() + "PID");
		if (paperRepository.existsById(paper.getPaperId())) {
			System.out.println(1);
			// get currently saved answers
			List<PaperQuestion> currentlySavedAnswers = getPaperQuestionList(paper.getPaperId());
			// delete currently saved answers of the paper
			paperQuestionRepository.deleteQuestionsOfaPaper(paper.getPaperId());
			System.out.println(2);
			System.out.println(answers + "ANS array");

			// save new answers of the paper
			String str = answers;
			Integer size = 4;
			String[] tokens = str.split("(?<=\\G.{" + size + "})"); // Split answers String
			int n = 0;
			for (n = 0; n < tokens.length; n++) {
				System.out.println(tokens[n] + "Tokens");
				if (tokens[n].length() == paper.getNo_of_answers()) { // checks size of a substring with no of answers
					System.out.println(tokens[n]);
					PaperQuestion quest = new PaperQuestion(0, paper, (n + 1), tokens[n]);
					PaperQuestion replyQ = paperQuestionRepository.save(quest); // saves a answer
					if (replyQ == null) { // error in saving a answer
						paperQuestionRepository.deleteQuestionsOfaPaper(paper.getPaperId()); // delete currently submitted answers
						paperQuestionRepository.saveAll(currentlySavedAnswers);
						break;
					}
//					System.out.println(4);
				}
			}
			if (n == tokens.length)
				return 1; // If all answers are saved return success
			else {
				paperQuestionRepository.deleteQuestionsOfaPaper(paper.getPaperId()); // delete currently submitted answers
				paperQuestionRepository.saveAll(currentlySavedAnswers); // save old answers again
				return null; //return failure
			}
		}
		return null;
	}
}