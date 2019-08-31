package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Path;

public interface PathRepository extends JpaRepository<Path, Integer>{

	@Query(value="select * from path u where u.path_name = :pathName and u.origin = :origin and u.destination = :destination",nativeQuery=true)
	public Path findByPathDetails(@Param("pathName")String pathName ,@Param("origin")String origin ,@Param("destination")String destination);
	
	@Query(value="select * from path u where u.path_id = :pathId",nativeQuery=true)
	public Path findByPathId(@Param("pathId") Integer pathId);
}
