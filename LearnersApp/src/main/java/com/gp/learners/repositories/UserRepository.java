package com.gp.learners.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gp.learners.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value="select * from user u where u.email = :email",nativeQuery=true)
	User findByEmail(@Param("email") String email);
	
	@Query("from User where Nic = :nic")
	public User findByNic(@Param("nic")String nic);
	
	@Query("from User where nic = :nic and email = :email")
	public User findByEmailAndNic(@Param("email") String email,@Param("nic") String nic);
	
	List<User> findUserIdStaffIdNameRoleStatusByRoleNot(Integer role);
	
	@Query(value="select * from user u WHERE u.user_id = :userId ",nativeQuery=true)
	User findByUserId(@Param("userId")Integer userId);
	
	@Query(value="select * from user where status=1",nativeQuery=true)
	List<User> getActiveUsers();
	
	

}
