package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private Endereco endereco;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Documento> documentos;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Pagamento> pagamentos;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Processo> processos;
	

}
