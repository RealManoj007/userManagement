package com.tecno.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tecno.dto.LoginDto;
import com.tecno.dto.RegisterDto;
import com.tecno.dto.ResetPwdDto;
import com.tecno.dto.UserDto;
import com.tecno.service.UserService;

@Controller 
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String regiterPage(Model model) {
		
		model.addAttribute("registerDTO",new RegisterDto());

		Map<Integer,String> countriesMap = userService.getCountries();
		model.addAttribute("countries",countriesMap);
		
		return "registerview";
	}

	@GetMapping("/states/{cid}")
	@ResponseBody  //For sending direct response we are using ResponseBody
	public Map<Integer, String> getStates(@PathVariable("cid") Integer cid){
		return userService.getStates(cid);
	}
	
	@GetMapping("/cities/{sid}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable("sid") Integer sid){
		return userService.getCities(sid);
	}
	
	@PostMapping("/register")
	public String register(RegisterDto registerDto, Model model) {
		UserDto user = userService.getUser(registerDto.getEmail());
		if(user != null) {
			model.addAttribute("emsg","Duplicate Email");
			return "registerview";
		}
		
		boolean registerUser = userService.registerUser(registerDto);
		if(registerUser) {
			model.addAttribute("smsg","User Registered");
		}else {
			model.addAttribute("emsg","Registeration Failed");
		}
		
		return "registerView";
	}
	
	@GetMapping("/")
	public String loginPage(Model model) {
		model.addAttribute("loginDto",new LoginDto());
		return "index";
	}
	
	@PostMapping("/login")
	public String login(LoginDto loginDto, Model model) {
		
		UserDto user = userService.getUser(loginDto);
		if(user == null) {
			model.addAttribute("emsg","Invalid Credentials");
			return "index";
		}
		String pwdUpdate = user.getPwdUpdate();
		if(pwdUpdate.equalsIgnoreCase("Yes")) {
			//pwd updated go to dashboard
			return "redirect:dashboard";
		}else {
			//pwd not updated go to pwd reset page
			ResetPwdDto resetPwdDto=new ResetPwdDto();
			resetPwdDto.setEmail(user.getEmail());
			model.addAttribute("resetPwdDto", resetPwdDto);
			return "resetPwdView";	
		}
	}
	
	@PostMapping("/resetpwd")
	public String resetPwd(ResetPwdDto pwdDto, Model model) {
		
		if(!(pwdDto.getNewPwd().equalsIgnoreCase(pwdDto.getConfirmPwd()))) {
			model.addAttribute("emsg","New Pwd and confirmed Pwd should be the same");
			return "resetPwdView";	
		}
		
		UserDto user = userService.getUser(pwdDto.getEmail());
		if(user.getPwd().equalsIgnoreCase(pwdDto.getOldPwd())) {
			boolean resetPwd = userService.resetPwd(pwdDto);
			if(resetPwd){
				return "redirect:dashboard";
			}else {
				model.addAttribute("emsg","Password update failed");
				return "resetPwdView";
			}
		}else {
			model.addAttribute("emsg","Old Password is wrong");
			return "resetPwdView";
		}	
	}
	
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		
		String quote = userService.getQuote();
		model.addAttribute("quote",quote);
		return "dashboard";
		
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/"; //this will load the binding object
	}
	
}
