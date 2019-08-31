package com.gp.learners.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sub_path")
public class SubPath {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subPathId;

	@NotBlank(message="SubPath name is madatory.")
	private String name;

	@ManyToOne
	@JoinColumn(name = "path_id", referencedColumnName = "path_id")
	private Path pathId;

	public SubPath() {

	}

	public SubPath(Integer subPathId, @NotBlank String name, Path pathId) {
		super();
		this.subPathId = subPathId;
		this.name = name;
		this.pathId = pathId;
	}

	public Integer getSubPathId() {
		return subPathId;
	}

	public void setSubPathId(Integer subPathId) {
		this.subPathId = subPathId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Path getPathId() {
		return pathId;
	}

	public void setPathId(Path pathId) {
		this.pathId = pathId;
	}

	@Override
	public String toString() {
		return "SubPath [subPathId=" + subPathId + ", name=" + name + ", pathId=" + pathId + "]";
	}

}
