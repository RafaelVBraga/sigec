package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Cliente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy = GenerationType.AUTO)	
	private UUID id;
	@NotBlank
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;	
	@CPF@NotBlank
	private String cpf;
	private String cpfDigital;
	@Size(min=11, message="Tamanho deve ser no mínimo 11")@NotBlank
	private String rg;
	private String rgDigital;
	@Email@NotBlank
	private String email;
	private String telefone;
	
	private LocalDate dataCadastro;
	@OneToOne(mappedBy = "cliente")
	private Endereco endereco;
	private String enderecoDigital;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Documento> documentos;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Pagamento> pagamentos;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Processo> processos;
	
	@ManyToMany
	@JoinTable(name ="cliente_representantes", joinColumns = @JoinColumn(name="cliente_id"), inverseJoinColumns = @JoinColumn(name="representante_id"))
	private List<Cliente> representantes;
	@ManyToMany
	@JoinTable(name ="cliente_herdeiros", joinColumns = @JoinColumn(name="cliente_id"), inverseJoinColumns = @JoinColumn(name="herdeiro_id"))
	private List<Cliente> herdeiros;
	
	
	

}
