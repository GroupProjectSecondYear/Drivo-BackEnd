package com.gp.learners.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.PaperQuestion;
import com.gp.learners.repositories.PaperRepository;
import com.gp.learners.repositories.PaperQuestionRepository;

@Service
public class PaperQuestionService {

	@Autowired
	PaperQuestionRepository paperQuestionRepository;

	// getPaperList
	public List<PaperQuestion> getPaperQuestionList(Integer paperId) {
		System.out.println("IN paperQuestrepo");
		List<PaperQuestion> paperQuestionList = paperQuestionRepository.getAnswersOfaPaper(paperId);
		// Paper paper1=paperList.get(0);
		return paperQuestionList;
	}

}