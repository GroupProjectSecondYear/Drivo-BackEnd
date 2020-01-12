package com.gp.learners.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

//Question Entity
@Entity
public class PaperQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Integer questionId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "paper_id", referencedColumnName = "paper_id")
	private Paper paperId;

	@NotNull
	private Integer question_no;
	
	@NotNull
	private String answer;

	public PaperQuestion() {

	}
	
	public PaperQuestion(Integer questionId, @NotNull Paper paperId, @NotNull Integer question_no, @NotNull String answer) {
		super();
		this.questionId = questionId;
		this.paperId = paperId;
		this.question_no = question_no;
		this.answer = answer;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Paper getPaperId() {
		return paperId;
	}

	public void setPaperId(Paper paperId) {
		this.paperId = paperId;
	}

	public Integer getQuestion_no() {
		return question_no;
	}

	public void setQuestion_no(Integer question_no) {
		this.question_no = question_no;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", paperId=" + paperId + ", question_no=" + question_no
				+ ", answer=" + answer + "]";
	}

	
	
}
