package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Processo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String numero;	
	private String tipo;
	private String status;
	private LocalDate dataCadastro;
	private LocalDate dataConclusao;
	private Double valor;
	private String descricao;
	@ManyToOne(cascade = CascadeType.ALL)
	private Cliente cliente;
	@OneToOne
	private Pagamento pagamento;

}
