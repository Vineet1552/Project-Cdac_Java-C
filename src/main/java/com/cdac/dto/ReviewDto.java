package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReviewDto {
	private Long id;
    private Long userId;
    private Long washerId;
    private Integer rating;
    private String comment;
    private String reviewDate;
}
