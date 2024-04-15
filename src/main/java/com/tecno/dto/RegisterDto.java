package com.tecno.dto;

import lombok.Data;

@Data
public class RegisterDto {

	private String name;
	private String email;
	private String phno;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	
}
