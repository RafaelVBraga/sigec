package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	private String nome;
	private LocalDate dataNascimento;
	private String genero;
	private String raca;
	private String cpf;
	private String rg;
	@OneToOne(mappedBy = "cliente")
	private Endereco endereco;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Documento> documentos;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Pagamento> pagamentos;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "cliente")
	private List<Processo> processos;
	

}
