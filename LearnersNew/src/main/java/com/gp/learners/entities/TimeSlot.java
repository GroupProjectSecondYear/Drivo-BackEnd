package com.gp.learners.entities;


import java.time.LocalTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "time_slot")
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="time_slot_id")
	private Integer timeSlotId;

	@NotNull(message = "from time is mandatory")
	private LocalTime startTime;

	@NotNull(message = "To time is mandatory")
	private LocalTime finishTime;

	public TimeSlot() {

	}

	public Integer getTimeSlotId() {
		return timeSlotId;
	}

	public TimeSlot(Integer timeSlotId, @NotNull(message = "from time is mandatory") LocalTime startTime,
			@NotNull(message = "To time is mandatory") LocalTime finishTime) {
		super();
		this.timeSlotId = timeSlotId;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public void setTimeSlotId(Integer timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalTime finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "TimeSlot [timeSlotId=" + timeSlotId + ", startTime=" + startTime + ", finishTime=" + finishTime + "]";
	}

}
