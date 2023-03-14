package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Pagamento implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =GenerationType.UUID )
	private UUID id;
	private LocalDate dataPagamento;
	private LocalDate dataVencimento;
	private Float valor;
	@ManyToOne(fetch = FetchType.EAGER)
	private Cliente cliente;
	@ManyToOne(fetch = FetchType.EAGER)
	private Processo processo;

}
