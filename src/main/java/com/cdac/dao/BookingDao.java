package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.BookingEntity;

public interface BookingDao extends JpaRepository<BookingEntity, Long> {

}
