package com.rvbraga.sigec.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsersDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private Boolean cargoGerencial;

}
