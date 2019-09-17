package com.gp.learners.entities.mapObject;

import java.util.ArrayList;
import java.util.List;

//Controller-->Time Table Controller
//service --> Time Table Service
//Repository -> Lesson Repository
//Reason --> Use in Lesson repository for get Lesson Specific format
public class LessonMap {

	private String day;
	private ArrayList<String> timeSlotData;
	private ArrayList<List<String>> packageData;
	private ArrayList<List<String>> instructorData;
	private ArrayList<List<String>> pathData;
	private ArrayList<List<Integer>> numStuData;
	private ArrayList<List<Integer>> idData;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public ArrayList<String> getTimeSlotData() {
		return timeSlotData;
	}

	public void setTimeSlotData(ArrayList<String> timeSlotData) {
		this.timeSlotData = timeSlotData;
	}

	public ArrayList<List<String>> getPackageData() {
		return packageData;
	}

	public void setPackageData(ArrayList<List<String>> packageData) {
		this.packageData = packageData;
	}

	public ArrayList<List<String>> getInstructorData() {
		return instructorData;
	}

	public void setInstructorData(ArrayList<List<String>> instructorData) {
		this.instructorData = instructorData;
	}

	public ArrayList<List<String>> getPathData() {
		return pathData;
	}

	public void setPathData(ArrayList<List<String>> pathData) {
		this.pathData = pathData;
	}

	public ArrayList<List<Integer>> getNumStuData() {
		return numStuData;
	}

	public void setNumStuData(ArrayList<List<Integer>> numStuData) {
		this.numStuData = numStuData;
	}

	public ArrayList<List<Integer>> getIdData() {
		return idData;
	}

	public void setIdData(ArrayList<List<Integer>> idData) {
		this.idData = idData;
	}

	@Override
	public String toString() {
		return "LessonMap [day=" + day + ", timeSlotData=" + timeSlotData + ", packageData=" + packageData
				+ ", instructorData=" + instructorData + ", pathData=" + pathData + ", numStuData=" + numStuData
				+ ", idData=" + idData + "]";
	}

}
