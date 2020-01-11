package com.gp.learners.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gp.learners.entities.Paper;
import com.gp.learners.entities.User;
import com.gp.learners.repositories.PaperRepository;

@Service
public class PaperService {

	@Autowired
	PaperRepository paperRepository;

	@Autowired
	S3Service s3Service;

	@Value("${aws.s3.bucket.learning_material_paper}")
	private String bucketName;

	// getPaperList
	public List<Paper> getPaperList() {
		System.out.println("IN paperrepo");
		List<Paper> paperList = paperRepository.findAll();
		// Paper paper1=paperList.get(0);
		return paperList;
	}

	public Paper getPaperByID(Integer paperId) {
		if (paperId != null) {
			if (paperRepository.existsById(paperId)) {
				return paperRepository.getPaperById(paperId);
			}
		}
		return new Paper();
	}

	// Add PDF
	public Paper addPaper(Paper paper, Integer[] answers) {
		// System.out.println(answers.length+"qestions"+paper.getNo_of_questions());
		// System.out.println(answers.length+"answers"+paper.getNo_of_answers());
		// System.out.println(answers[18]+"19th");
		System.out.println("Papaer Servide add Method");
		// Paper result = paperRepository.save(paper);
		// if (result != null) {
		// save answers of paper
		String ans = answers[0].toString();
		for (int n = 1; n < answers.length; n++) {  //split array of answers
			ans += answers[n].toString();
			if (ans.length() == 4) {
				System.out.println(ans);
				ans = answers[++n].toString();
			}
		}
		return null;
	}

	// delete Paper
	public String deletePaper(Integer paperId) {
		System.out.println("Delete Paper Serv");
		if (paperId != null) {
			if (paperRepository.existsById(paperId)) {
				paperRepository.deleteById(paperId);
				return "success";
			}
		}
		return "error";
	}

	// update Student Details
	public Paper updatePaper(Paper paper) {
		if (paperRepository.existsById(paper.getPaperId())) {
			// Paper paper1=paperRepository.getPaperById(paper.getPaperId());
			return paperRepository.save(paper);
		}

		return new Paper();
	}

	public String uploadPaper(MultipartFile file, Integer paperId) {
		System.out.println("Paper UPLOADING");
		if (paperRepository.existsById(paperId)) {
			String keyName = paperId + ".paper";
			if (keyName != null) {
				s3Service.uploadFile(keyName, file, bucketName);
				Paper paper = paperRepository.getPaperById(paperId);
				// user.setProfileImage(1);
				// userRepository.save(user);
				System.out.println("Paper UPLOADING success");
				return "success";
			}
		}
		return null;
	}

	public ByteArrayOutputStream downloadPaper(Integer paperId) {
		if (paperRepository.existsById(paperId)) {
			System.out.println("PDF dwnlding Byte Array Method");
			String keyName = paperId + ".paper";

			if (paperId != null) {
				Paper paper = paperRepository.getPaperById(paperId);
				try {
					System.out.println("PDF dwnLOADING Byte Array Method try chtch");
					/*
					 * if (paper.getProfileImage() == 1) { return s3Service.downloadFile(keyName,
					 * bucketName); } else {
					 */
					return s3Service.downloadFile(keyName, bucketName);
					/* } */
				} catch (Exception e) {
					System.out.println("There is a problem in s3 bucket");
				}

			}
		}
		return null;
	}

}