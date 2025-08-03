package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NotificationDto {
	private Long id;
    private Long userId;
    private String message;
    private String type;
    private String status;
    private String createdAt;
}
