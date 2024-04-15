package com.tecno.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tecno.entity.StateEntity;

public interface StateRepo extends JpaRepository<StateEntity, Integer>{
	
//	This is another approach for getStates() method
//	@Query(value="select * from state_master where country_id=:cid", nativeQuery = true)
//	public List<StateEntity> getStates(Integer cid);
	
	
}
