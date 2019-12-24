package com.gp.learners.entities.mapObject;

//Controller-->Time Table Controller
//service --> Time Table Service
//Repository -> Lesson Repository
//Reason --> Use in getStudentAttendance() function for assign student attendance details
public class StudentAttendanceWeeksMap {

	private String week;
	private Integer numStudent;

	public StudentAttendanceWeeksMap() {

	}

	public StudentAttendanceWeeksMap(String week, Integer numStudent) {
		super();
		this.week = week;
		this.numStudent = numStudent;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Integer getNumStudent() {
		return numStudent;
	}

	public void setNumStudent(Integer numStudent) {
		this.numStudent = numStudent;
	}

	@Override
	public String toString() {
		return "StudentAttendanceWeeksMap [week=" + week + ", numStudent=" + numStudent + "]";
	}

}
