package com.rvbraga.sigec.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PesquisaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pesquisaNome;
	
	private String pesquisaCpf; 
	@Min(value = 1)
	private Integer paginas;
 
}
