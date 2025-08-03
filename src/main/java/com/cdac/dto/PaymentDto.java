package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PaymentDto {
	private Long id;
    private Long bookingId;
    private Double amount;
    private String paymentDate;
    private String paymentMethod;
    private String paymentStatus;
}
