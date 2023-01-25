package com.rvbraga.sigec.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MensagemDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String mensagem;
	
}
