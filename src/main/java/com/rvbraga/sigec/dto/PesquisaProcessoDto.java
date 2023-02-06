package com.rvbraga.sigec.dto;

import java.io.Serializable;

import lombok.Data;
@Data
public class PesquisaProcessoDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pesquisaNumero;
	private String pesquisaCliente;
	private Integer paginas;

}
