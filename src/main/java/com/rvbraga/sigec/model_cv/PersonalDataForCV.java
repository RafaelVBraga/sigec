package com.rvbraga.sigec.model_cv;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class PersonalDataForCV implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String email;
	private String dateOfBirth;
	private String location;
	private String linkedin;
	private String sex;
	private String nationality;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "personalDataForCV")
	private List<PersonalSkill> personalSkills;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "personalDataForCV")
	private List<WorkExperience> workExperiences;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "personalDataForCV")
	private List<AcademicExperience> academicSkills;
	
	
	
	
}
