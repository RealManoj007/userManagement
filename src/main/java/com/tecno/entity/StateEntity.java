package com.tecno.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="STATE_MASTER")
@Data
public class StateEntity {
	@Id
	private Integer stateId;
	private String stateName;
	@ManyToOne
	@JoinColumn(name="country_id")
	private CountryEntity country;
}