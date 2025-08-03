package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.VehicleEntity;

public interface VehicleDao extends JpaRepository<VehicleEntity, Long> {
	List<VehicleEntity> findByUserId(Long userId);
}
