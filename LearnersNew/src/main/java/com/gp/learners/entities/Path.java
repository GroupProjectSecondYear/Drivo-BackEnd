package com.gp.learners.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@Entity
public class Path {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "path_id")
	private Integer pathId;

	@NotBlank(message = "PathName may not be blank")
	private String pathName;

	@NotBlank(message = "Origin may not be blank")
	private String origin;

	@NotBlank(message = "Destination may not be blank")
	private String destination;

	public Path() {

	}

	public Path(Integer pathId, @NotBlank(message = "PathName may not be blank") String pathName,
			@NotBlank(message = "Origin may not be blank") String origin,
			@NotBlank(message = "Destination may not be blank") String destination) {
		super();
		this.pathId = pathId;
		this.pathName = pathName;
		this.origin = origin;
		this.destination = destination;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public Integer getPathId() {
		return pathId;
	}

	public void setPathId(Integer pathId) {
		this.pathId = pathId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Path [pathId=" + pathId + ", pathName=" + pathName + ", origin=" + origin + ", destination="
				+ destination + "]";
	}

}
