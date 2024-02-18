package com.rvbraga.sigec.dto;

import java.io.Serializable;

import com.rvbraga.sigec.security.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String password;
	private Boolean cargoGerencial;
	
	public UsersDto(Users user) {
		this.id = user.getId();
		Long counter = user.getAuthorities().stream().filter(authority -> authority.getAuthority().contentEquals("ADMIN")).count();
		if(counter>1) this.cargoGerencial=true; else this.cargoGerencial = false;
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

}
