package com.rvbraga.sigec.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Endereco;
import com.rvbraga.sigec.model.Telefone;

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
	private String profissao;
	private String raca;
	@CPF@NotBlank
	private String cpf;
	private String cpfDigital;
	@Size(min=11, message="Tamanho deve ser no mínimo 11")@NotBlank
	private String rg;
	private String rgDigital;
	@Email@NotBlank
	private String email;
	
	private List<Telefone>telefones;
	private Endereco endereco;
	private String enderecoDigital;
	
	public ClienteDto() {
		
	}
	
	public ClienteDto(Cliente cliente){
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		
		this.dataNascimento = cliente.getDataNascimento();		
		this.rg = cliente.getRg();	
		
		this.email = cliente.getEmail();
		this.telefones = cliente.getTelefones();
		this.endereco = cliente.getEndereco();		
		this.profissao = cliente.getProfissao();
	}

}
