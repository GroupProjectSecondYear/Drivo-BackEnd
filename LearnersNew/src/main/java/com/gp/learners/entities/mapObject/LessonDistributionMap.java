package com.gp.learners.entities.mapObject;

//Controller-->Time Table Controller
//service --> Time Table Service
//Repository -> lesson Repository
//Reason --> Use in lesson repository for assign the findByPackageIdAndType() query  results

public class LessonDistributionMap {

	Integer day;
	Long lessonCount;

	public LessonDistributionMap() {

	}

	public LessonDistributionMap(Integer day, Long lessonCount) {
		super();
		this.day = day;
		this.lessonCount = lessonCount;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Long getLessonCount() {
		return lessonCount;
	}

	public void setLessonCount(Long lessonCount) {
		this.lessonCount = lessonCount;
	}

	@Override
	public String toString() {
		return "LessonDistributionMap [day=" + day + ", lessonCount=" + lessonCount + "]";
	}

}
