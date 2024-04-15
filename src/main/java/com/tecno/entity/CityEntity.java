package com.tecno.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="CITY_MASTER")
@Data
public class CityEntity {
	@Id
	private Integer cityId;
	private String cityName;
	@ManyToOne
	@JoinColumn(name="state_id")
	private StateEntity state;
}
