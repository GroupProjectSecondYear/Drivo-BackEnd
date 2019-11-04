package com.gp.learners.entities.mapObject;

//Controller-->Staff Controller
//service --> Staff Service
//Reason --> Use in getStaffWorkDays() function
public class StaffWorkDaysDataMap {

	private Integer fullDays;
	private Integer halfDays;
	private Integer notCompleteDays;
	private Integer leaveDays;

	public Integer getFullDays() {
		return fullDays;
	}

	public void setFullDays(Integer fullDays) {
		this.fullDays = fullDays;
	}

	public Integer getHalfDays() {
		return halfDays;
	}

	public void setHalfDays(Integer halfDays) {
		this.halfDays = halfDays;
	}

	public Integer getNotCompleteDays() {
		return notCompleteDays;
	}

	public void setNotCompleteDays(Integer notCompleteDays) {
		this.notCompleteDays = notCompleteDays;
	}

	public Integer getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}

	@Override
	public String toString() {
		return "StaffWorkDaysDataMap [fullDays=" + fullDays + ", halfDays=" + halfDays + ", notCompleteDays="
				+ notCompleteDays + ", leaveDays=" + leaveDays + "]";
	}

}
