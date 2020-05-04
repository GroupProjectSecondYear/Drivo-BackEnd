package com.gp.learners.entities.mapObject;

import java.util.List;

import com.gp.learners.entities.TimeSlot;

public class LessonDayFeedbackChartDataMap {

	private List<Integer> totalRequest;
	private List<Integer> handleRequest;
	private List<Integer> extraRequest;
	private TimeSlot timeSlot;

	public LessonDayFeedbackChartDataMap() {

	}

	public LessonDayFeedbackChartDataMap(List<Integer> totalRequest, List<Integer> handleRequest,
			List<Integer> extraRequest, TimeSlot timeSlot) {
		super();
		this.totalRequest = totalRequest;
		this.handleRequest = handleRequest;
		this.extraRequest = extraRequest;
		this.timeSlot = timeSlot;
	}

	public List<Integer> getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(List<Integer> totalRequest) {
		this.totalRequest = totalRequest;
	}

	public List<Integer> getHandleRequest() {
		return handleRequest;
	}

	public void setHandleRequest(List<Integer> handleRequest) {
		this.handleRequest = handleRequest;
	}

	public List<Integer> getExtraRequest() {
		return extraRequest;
	}

	public void setExtraRequest(List<Integer> extraRequest) {
		this.extraRequest = extraRequest;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public String toString() {
		return "LessonDayFeedbackChartDataMap [totalRequest=" + totalRequest + ", handleRequest=" + handleRequest
				+ ", extraRequest=" + extraRequest + ", timeSlot=" + timeSlot + "]";
	}

}
