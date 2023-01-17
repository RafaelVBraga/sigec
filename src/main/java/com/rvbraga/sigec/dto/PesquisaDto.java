package com.rvbraga.sigec.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PesquisaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pesquisaNome;
	@Size(min=11, max=11)
	private String pesquisaCpf;

}
