package com.tecno.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecno.entity.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer>{

}
