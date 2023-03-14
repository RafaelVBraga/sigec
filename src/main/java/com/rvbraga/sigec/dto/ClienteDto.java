package com.rvbraga.sigec.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.rvbraga.sigec.model.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID id;
	@NotBlank
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	private String genero;
	private String raca;
	@CPF@NotBlank
	private String cpf;
	@Size(min=11, message="Tamanho deve ser no mínimo 11")@NotBlank
	private String rg;
	@Email@NotBlank
	private String email;
	private String telefone;
	
	public ClienteDto() {
		
	}
	
	public ClienteDto(Cliente cliente){
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.dataNascimento = cliente.getDataNascimento();		
		this.rg = cliente.getRg();		
		this.email = cliente.getEmail();
		this.telefone = cliente.getTelefone();
	}

}
