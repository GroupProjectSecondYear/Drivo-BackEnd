package com.gp.learners.entities.mapObject;

import java.util.ArrayList;

public class WebSocketCommunicationDataMap {

	/*
	 * 0 -> Admin 1 -> ASS 2 -> ASI 3 -> Instructor 4 -> Student
	 */
	private ArrayList<Integer> role;

	public ArrayList<Integer> getRole() {
		return role;
	}

	public void setRole(ArrayList<Integer> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "WebSocketCommunicationDataMap [role=" + role + "]";
	}

}
