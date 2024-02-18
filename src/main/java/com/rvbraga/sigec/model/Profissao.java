package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Profissao implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =GenerationType.UUID )
	private UUID id;
	private String nome;
	
}
