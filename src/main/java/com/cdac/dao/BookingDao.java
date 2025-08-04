package com.cdac.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.BookingEntity;
import com.cdac.entities.UserEntity;


public interface BookingDao extends JpaRepository<BookingEntity, Long> {
	List<BookingEntity> findByUser(UserEntity user);

}
