package com.gp.learners.entities;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExamResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer examResultId;

	private LocalDate date;
	private Integer writtenExam;
	private Integer trialExam;
	private Integer writtenExamFail;
	private Integer trialExamFail;
	
	public ExamResult() {
		
	}
	
	public ExamResult(Integer examResultId, LocalDate date, Integer writtenExam, Integer trialExam, Integer writtenExamFail,
			Integer trialExamFail) {
		super();
		this.examResultId = examResultId;
		this.date = date;
		this.writtenExam = writtenExam;
		this.trialExam = trialExam;
		this.writtenExamFail = writtenExamFail;
		this.trialExamFail = trialExamFail;
	}

	public Integer getExamResultId() {
		return examResultId;
	}

	public void setExamResultId(Integer examResultId) {
		this.examResultId = examResultId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getWrittenExam() {
		return writtenExam;
	}

	public void setWrittenExam(Integer writtenExam) {
		this.writtenExam = writtenExam;
	}

	public Integer getTrialExam() {
		return trialExam;
	}

	public void setTrialExam(Integer trialExam) {
		this.trialExam = trialExam;
	}

	public Integer getWrittenExamFail() {
		return writtenExamFail;
	}

	public void setWrittenExamFail(Integer writtenExamFail) {
		this.writtenExamFail = writtenExamFail;
	}

	public Integer getTrialExamFail() {
		return trialExamFail;
	}

	public void setTrialExamFail(Integer trialExamFail) {
		this.trialExamFail = trialExamFail;
	}

	@Override
	public String toString() {
		return "ExamResult [examResultId=" + examResultId + ", date=" + date + ", writtenExam=" + writtenExam
				+ ", trialExam=" + trialExam + ", writtenExamFail=" + writtenExamFail + ", trialExamFail="
				+ trialExamFail + "]";
	}

}
