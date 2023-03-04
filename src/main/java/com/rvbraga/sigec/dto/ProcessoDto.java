package com.rvbraga.sigec.dto;

import java.io.Serializable;
import java.util.UUID;

import com.rvbraga.sigec.model.Processo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private UUID idCliente;
	private UUID id;
	@NotNull@NotBlank
	private String numero;
	private String status;
	private String tipo;	
	
	public ProcessoDto() {
		
	}
	
    public 	ProcessoDto(Processo processo) {
    	
    	this.id = processo.getId();
    	this.idCliente = processo.getCliente().getId();
    	this.numero = processo.getNumero();
    	this.status = processo.getStatus();
    	this.tipo = processo.getTipo();    	
    	
    }
    
	
	
	
	

}
