package com.tecno.dto;

import lombok.Data;

//DTO should be used in Controller and Presentation Layer
//Entity should be used in Persistence Layer

@Data
public class UserDto {

	private Integer userId;
	private String name;
	private String email;
	private String phno;
	private String pwd;
	private String pwdUpdate;
	private String newPwd;
	private String confirmPwd;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	
}
