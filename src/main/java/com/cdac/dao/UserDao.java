package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}
