package com.tecno.service;

import java.util.Map;

import com.tecno.dto.LoginDto;
import com.tecno.dto.RegisterDto;
import com.tecno.dto.ResetPwdDto;
import com.tecno.dto.UserDto;

public interface UserService {

	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer cid);
	
	public Map<Integer, String> getCities(Integer sid);
	
	public UserDto getUser(String email);
	
	public boolean registerUser(RegisterDto registerDto);
	
	public UserDto getUser(LoginDto loginDto);
	
	public boolean resetPwd(ResetPwdDto resetPwdDto);
	
	public String getQuote();
	
}
