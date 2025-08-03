package com.cdac.service;

import java.util.List;

import com.cdac.dto.PaymentDto;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto dto);
    List<PaymentDto> getAllPayments();
    PaymentDto getPaymentById(Long id);
    void deletePayment(Long id);
}