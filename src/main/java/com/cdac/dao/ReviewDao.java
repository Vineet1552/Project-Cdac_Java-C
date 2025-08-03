package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.ReviewEntity;

public interface ReviewDao extends JpaRepository<ReviewEntity, Long> {

}
