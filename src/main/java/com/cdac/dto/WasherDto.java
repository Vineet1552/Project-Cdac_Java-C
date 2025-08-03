package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WasherDto {
	private Long id;
    private String name;
    private String email;
    private String phone;
    private String status;
    private Double rating;
    private String area;
}
