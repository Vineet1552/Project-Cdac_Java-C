package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.WasherEntity;

public interface WasherDao extends JpaRepository<WasherEntity, Long> {

}
