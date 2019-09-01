package com.gp.learners.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.Path;
import com.gp.learners.entities.SubPath;

public interface SubPathRepository extends JpaRepository<SubPath,Integer>{
	
	@Query(value="select sub_path_id from sub_path u where u.path_id = :pathId",nativeQuery=true)
	public List<Integer> findByPathIdAndGetIds(@Param("pathId")Path pathId);
	
	@Query(value="select name from sub_path u where u.path_id = :pathId",nativeQuery=true)
	public List<String> findByPathId(@Param("pathId")Path pathId);
	
//	@Modifying
//	@Query(value="delete from sub_path u where u.path_id = :pathId",nativeQuery=true)
//	public void deleteByPathId(@Param("pathId") Path pathId);
	
//	@Query(value="select sub_path_id from sub_path u where u.name = :name and u.path_id = :pathId",nativeQuery=true)
//	public Integer findByPathIdAndName(@Param("pathId")Path pathId,@Param("name")String name);
}
