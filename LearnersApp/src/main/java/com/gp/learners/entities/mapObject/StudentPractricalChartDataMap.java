package com.gp.learners.entities.mapObject;

import java.time.LocalDate;

//Controller-->Instructor Controller
//service --> Instructor Service
//Reason --> Use in Instructor Service  getPractricalLessonChartStudentData() function
public class StudentPractricalChartDataMap {

	private String packageName;
	private Integer completeLesson;
	private Integer notCompleteLesson;
	private Integer remainLesson;
	private Integer bookLesson;
	private LocalDate trialExamDate;
	private Long remainDays;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Integer getCompleteLesson() {
		return completeLesson;
	}

	public void setCompleteLesson(Integer completeLesson) {
		this.completeLesson = completeLesson;
	}

	public Integer getNotCompleteLesson() {
		return notCompleteLesson;
	}

	public void setNotCompleteLesson(Integer notCompleteLesson) {
		this.notCompleteLesson = notCompleteLesson;
	}

	public Integer getRemainLesson() {
		return remainLesson;
	}

	public void setRemainLesson(Integer remainLesson) {
		this.remainLesson = remainLesson;
	}

	public Integer getBookLesson() {
		return bookLesson;
	}

	public void setBookLesson(Integer bookLesson) {
		this.bookLesson = bookLesson;
	}

	public LocalDate getTrialExamDate() {
		return trialExamDate;
	}

	public void setTrialExamDate(LocalDate trialExamDate) {
		this.trialExamDate = trialExamDate;
	}

	public Long getRemainDays() {
		return remainDays;
	}

	public void setRemainDays(Long remainDays) {
		this.remainDays = remainDays;
	}

	@Override
	public String toString() {
		return "StudentPractricalChartDataMap [packageName=" + packageName + ", completeLesson=" + completeLesson
				+ ", notCompleteLesson=" + notCompleteLesson + ", remainLesson=" + remainLesson + ", bookLesson="
				+ bookLesson + ", trialExamDate=" + trialExamDate + ", remainDays=" + remainDays + "]";
	}

}
