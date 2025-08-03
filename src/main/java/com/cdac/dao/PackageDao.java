package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.PackageEntity;

public interface PackageDao extends JpaRepository<PackageEntity, Long> {

}
