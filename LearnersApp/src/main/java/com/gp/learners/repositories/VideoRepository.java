package com.gp.learners.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Video;


public interface VideoRepository extends JpaRepository<Video, Integer>{

	//get video by id
	@Query("from Video where videoId = :videoId")
	public Video getVideoById(@Param("videoId")Integer videoId);



}