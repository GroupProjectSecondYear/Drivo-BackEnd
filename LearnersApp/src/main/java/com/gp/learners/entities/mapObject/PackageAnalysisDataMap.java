package com.gp.learners.entities.mapObject;

import java.util.ArrayList;

import com.gp.learners.entities.TimeSlot;

//Controller-->Time Table Controller
//service --> Time Table Service
//Repository -> Lesson Repository
//Reason --> Use in getLessonsByPackageId() function for assign lesson's timetable details
public class PackageAnalysisDataMap {

	private String day;
	private ArrayList<TimeSlot> timeSlot;
	private ArrayList<Integer> student;
	private ArrayList<Double> stuPercentage;

	public PackageAnalysisDataMap() {

	}

	public PackageAnalysisDataMap(String day, ArrayList<TimeSlot> timeSlot, ArrayList<Integer> student,
			ArrayList<Double> stuPercentage) {
		super();
		this.day = day;
		this.timeSlot = timeSlot;
		this.student = student;
		this.stuPercentage = stuPercentage;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public ArrayList<TimeSlot> getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(ArrayList<TimeSlot> timeSlot) {
		this.timeSlot = timeSlot;
	}

	public ArrayList<Integer> getStudent() {
		return student;
	}

	public void setStudent(ArrayList<Integer> student) {
		this.student = student;
	}

	public ArrayList<Double> getStuPercentage() {
		return stuPercentage;
	}

	public void setStuPercentage(ArrayList<Double> stuPercentage) {
		this.stuPercentage = stuPercentage;
	}

	@Override
	public String toString() {
		return "PackageAnalysisDataMap [day=" + day + ", timeSlot=" + timeSlot + ", student=" + student
				+ ", stuPercentage=" + stuPercentage + "]";
	}

}
