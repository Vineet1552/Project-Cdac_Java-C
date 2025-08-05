package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.PackageEntity;
import com.cdac.entities.WasherEntity;

public interface PackageDao extends JpaRepository<PackageEntity, Long> {

	List<PackageEntity> findByWasher(WasherEntity washer);

}
