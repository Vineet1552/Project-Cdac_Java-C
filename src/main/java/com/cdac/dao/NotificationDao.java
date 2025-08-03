package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.NotificationEntity;

public interface NotificationDao extends JpaRepository<NotificationEntity, Long> {

}
