package com.tecno.dto;

import lombok.Data;

@Data
public class ResetPwdDto {

	private Integer userId;
	private String email;
	private String oldPwd;
	private String newPwd;
	private String confirmPwd;
}
