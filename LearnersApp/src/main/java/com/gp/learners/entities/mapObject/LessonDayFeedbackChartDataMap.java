package com.gp.learners.entities.mapObject;

public class LessonDayFeedbackChartDataMap {

	private Integer day;
	private Long count;
	
	public LessonDayFeedbackChartDataMap() {
		
	}

	public LessonDayFeedbackChartDataMap(Integer day, Long count) {
		super();
		this.day = day;
		this.count = count;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "LessonDayFeedbackChartDataMap [day=" + day + ", count=" + count + "]";
	}

}
