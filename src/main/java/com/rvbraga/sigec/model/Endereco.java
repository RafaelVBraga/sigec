package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
@Entity
public class Endereco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String logradouro;
	private String numero;
	private String complemento;
	private String cep;
	private String bairro;
	private String digitalizacao;
	@OneToOne(cascade=CascadeType.ALL)
	private Cliente cliente;
	
		
	

}
