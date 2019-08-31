package com.gp.learners.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gp.learners.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{

}
