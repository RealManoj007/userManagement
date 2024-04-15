package com.tecno.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecno.dto.LoginDto;
import com.tecno.dto.QuoteDto;
import com.tecno.dto.RegisterDto;
import com.tecno.dto.ResetPwdDto;
import com.tecno.dto.UserDto;
import com.tecno.entity.CityEntity;
import com.tecno.entity.CountryEntity;
import com.tecno.entity.StateEntity;
import com.tecno.entity.UserEntity;
import com.tecno.repo.CityRepo;
import com.tecno.repo.CountryRepo;
import com.tecno.repo.StateRepo;
import com.tecno.repo.UserRepo;
import com.tecno.utils.EmailUtils;


//DTO should be used in Controller and Presentation Layer
//Entity should be used in Persistence Layer


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	@Autowired
	private CityRepo cityRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public Map<Integer, String> getCountries() {
		Map<Integer, String> countryMap = new HashMap<>();
		
		List<CountryEntity> countryList = countryRepo.findAll();
		
		countryList.forEach(c->{
			countryMap.put(c.getCountryId(), c.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer cid) {
		
		Map<Integer, String> stateMap=new HashMap<>();
		
		CountryEntity countryEntity=new CountryEntity();
		countryEntity.setCountryId(cid);
		
		StateEntity stateEntity=new StateEntity();
		stateEntity.setCountry(countryEntity);
		
		Example<StateEntity> exampleStateEntity = Example.of(stateEntity);
		
		List<StateEntity> allStates = stateRepo.findAll(exampleStateEntity);
		
		allStates.forEach(e->{
			stateMap.put(e.getStateId(), e.getStateName());
		});
		
		return stateMap;
	}//we can also use native queries but but not HQL because of FK

	@Override
	public Map<Integer, String> getCities(Integer sid) {
		Map<Integer, String> citiesMap=new HashMap<>();
		List<CityEntity> cities = cityRepo.getCities(sid);
		cities.forEach(c->{
			citiesMap.put(c.getCityId(), c.getCityName());
		});
		return citiesMap;
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);

//		UserDto dto=new UserDto();
//		BeanUtils.copyProperties(userEntity, dto);
		
//		ObjectMapper objectMapper=new ObjectMapper();
//		UserEntity entity;
//		try {
//			entity = objectMapper.readValue(objectMapper.writeValueAsString(dto),UserEntity.class);
//			System.err.println(entity);
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ModelMapper mapper=new ModelMapper();
		UserDto userDTO = mapper.map(userEntity, UserDto.class);
		
		
		return userDTO;
	}

	@Override
	public boolean registerUser(RegisterDto registerDto) {
		ModelMapper mapper=new ModelMapper();
		UserEntity userEntity = mapper.map(registerDto, UserEntity.class);
		System.err.println("values after mapping is "+userEntity);
		
		CountryEntity country = countryRepo.findById(registerDto.getCountryId()).orElseThrow();
		StateEntity state = stateRepo.findById(registerDto.getStateId()).orElseThrow();
		CityEntity city = cityRepo.findById(registerDto.getCityId()).orElseThrow();
		
		userEntity.setCountry(country);
		userEntity.setState(state);
		userEntity.setCity(city);
		userEntity.setPwd(generateRandom());
		userEntity.setPwdUpdate("no");
		
		UserEntity userSavedEntity = userRepo.save(userEntity);
		
		String subject = "USer registration";
		
		String body = "Your temporary password is : "+userEntity.getPwd();
		
		boolean sendEmail = emailUtils.sendEmail(registerDto.getEmail(), subject, body);
		
		
		
		return userSavedEntity.getUserId()!=null;
	}

	@Override
	public UserDto getUser(LoginDto loginDto) {
		UserEntity userEntity = userRepo.findByEmailAndPwd(loginDto.getEmail(), loginDto.getPwd());
		
		if(userEntity ==  null) { return null; }
		
		ModelMapper mapper=new ModelMapper();
		return mapper.map(userEntity, UserDto.class);
	}

	@Override
	public boolean resetPwd(ResetPwdDto resetPwdDto) {
		UserEntity userEntity = userRepo.findByEmailAndPwd(resetPwdDto.getEmail(), resetPwdDto.getOldPwd());
		
		if(userEntity != null) {
			userEntity.setPwd(resetPwdDto.getNewPwd());
			userEntity.setPwdUpdate("Yes");
			userRepo.save(userEntity);
			return  true;
		}
		return false;
	}

	@Override
	public String getQuote() {
		
		QuoteDto[] quotations=null; 
		
		String url="https://type.fit/api/quotes";
		
		//web service call
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> forEntity = rt.getForEntity(url, String.class);
		System.err.println(forEntity);
		String responseBody = forEntity.getBody();
		System.err.println(responseBody);
		ObjectMapper mapper=new ObjectMapper();
		
		try {
			quotations=mapper.readValue(responseBody, QuoteDto[].class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//For Random Quotation
		Random random=new Random();
		int index=random.nextInt(quotations.length-1);
		
		return quotations[index].getText();
	}
	
	public static String generateRandom() {
		String aToz="ABCDEFGHIJKLMNOPQRSTUVWXY1234567890";
		Random random=new Random();
		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<5;i++) {
			int randomIndex=random.nextInt(aToz.length());
			stringBuilder.append(aToz.charAt(randomIndex));
		}
		return stringBuilder.toString();
	}
}
