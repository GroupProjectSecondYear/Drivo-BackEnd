package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.LeaveSetting;

public interface LeaveSettingRepository extends JpaRepository<LeaveSetting, Integer>{

}
