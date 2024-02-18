package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Usuario implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String username;
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "responsavel")
	private List<Agendamento> agendamentos;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "responsavel")
	private List<Processo> processos;

	

}
