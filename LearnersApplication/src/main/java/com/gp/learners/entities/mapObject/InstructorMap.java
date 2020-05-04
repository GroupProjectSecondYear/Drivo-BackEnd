package com.gp.learners.entities.mapObject;

import javax.validation.constraints.NotBlank;

//Controller-->Time Table Controller
//service --> Time Table Service
//Repository -> instructor Repository
//Reason --> Use in instructor repository for assign the query results
public class InstructorMap {

	@NotBlank(message = "Instructor Id is mandatory")
	private Integer instructorId;

	@NotBlank(message = "Instructor name is mandatory")
	private String name;

	public InstructorMap() {

	}

	public InstructorMap(@NotBlank(message = "Instructor Id is mandatory") Integer instructorId,
			@NotBlank(message = "Instructor name is mandatory") String name) {
		super();
		this.instructorId = instructorId;
		this.name = name;
	}

	public Integer getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Integer instructorId) {
		this.instructorId = instructorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "InstructorMap [instructorId=" + instructorId + ", name=" + name + "]";
	}

}
