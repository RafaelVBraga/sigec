package com.rvbraga.sigec.model_cv;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data@Entity
public class WorkExperience implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	private String position;
	private LocalDate startDate;
	private String description;
	private LocalDate endDate;
	private String enterprise;	
	@JsonIgnore
	@ManyToOne
	private PersonalDataForCV personalDataForCV;
		
}