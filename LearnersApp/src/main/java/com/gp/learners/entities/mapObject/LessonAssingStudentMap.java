package com.gp.learners.entities.mapObject;

//Controller-->Instructor Controller
//service --> Instructor Service
//Repository -> StudentLesson Repository
//Reason --> Use in instructor Service for assign studentLessonDetails.use in getAssignStudent() function.
public class LessonAssingStudentMap {

	private Integer studentLessonId;
	private String name;
	private String nic;
	private Integer complete;

	public LessonAssingStudentMap() {

	}

	public Integer getStudentLessonId() {
		return studentLessonId;
	}

	public void setStudentLessonId(Integer studentLessonId) {
		this.studentLessonId = studentLessonId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	@Override
	public String toString() {
		return "LessonAssingStudentMap [studentLessonId=" + studentLessonId + ", name=" + name + ", nic=" + nic
				+ ", complete=" + complete + "]";
	}

}
