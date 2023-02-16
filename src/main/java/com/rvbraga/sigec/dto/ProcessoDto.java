package com.rvbraga.sigec.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class ProcessoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UUID idCliente;
	private UUID id;
	private String numero;
	private String status;
	private String tipo;
	private String descricao;
	private Double valor;
	private LocalDate dataCadastro;
	private LocalDate dataConclusao;
	
	

}
