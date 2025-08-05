package com.cdac.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.WasherEntity;

public interface WasherDao extends JpaRepository<WasherEntity, Long> {

	Optional<WasherEntity> findByEmail(String email);

}
