package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data@Entity
public class Agendamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String status;
	private String descricao;
	private String assunto;
	private String tipo;
	private LocalDate dataRegistro;
	private LocalDate dataAgendamento;	
	private LocalTime hora;
	private String dia;
	private String mes;
	private String ano;
	@ManyToOne
	private Usuario responsavelAgendamento;
	@ManyToOne
	private Cliente cliente;
	

}
