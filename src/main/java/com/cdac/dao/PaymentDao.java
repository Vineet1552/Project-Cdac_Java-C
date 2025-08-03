package com.cdac.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.entities.PaymentEntity;

public interface PaymentDao extends JpaRepository<PaymentEntity, Long> {

}
