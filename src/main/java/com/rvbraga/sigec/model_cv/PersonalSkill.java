package com.rvbraga.sigec.model_cv;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data@Entity
public class PersonalSkill implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	private String level;	
	private String description;
	private String institution;	
	@JsonIgnore
	@ManyToOne
	private PersonalDataForCV personalDataForCV;
		
}