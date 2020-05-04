package com.gp.learners.entities.mapObject;

import java.util.Arrays;
import java.util.List;

import com.gp.learners.entities.Paper;

public class PaperAnswerMap {
	
	private Paper paper;
    private String answers;
	public PaperAnswerMap(Paper paper, String answer) {
		super();
		this.paper = paper;
		this.answers = answer;
	}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answer) {
		this.answers = answer;
	}
	@Override
	public String toString() {
		return "PaperAnswerMap [paper=" + paper + ", answer=" + answers + "]";
	}
	public PaperAnswerMap() {
		
	}
    
	
}
