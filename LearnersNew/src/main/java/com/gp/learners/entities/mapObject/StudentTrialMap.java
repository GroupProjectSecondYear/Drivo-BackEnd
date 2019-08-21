package com.gp.learners.entities.mapObject;

//use to return student trial list
public class StudentTrialMap {

	private String name;
	private String nic;
	
	public StudentTrialMap() {
		
	}

	public StudentTrialMap(String name, String nic) {
		super();
		this.name = name;
		this.nic = nic;
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

	@Override
	public String toString() {
		return "StudentTrialMap [name=" + name + ", nic=" + nic + "]";
	}

}
