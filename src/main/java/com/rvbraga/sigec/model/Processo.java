package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Processo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String descricao;
	private String numero;
	private String tipo;
	private String status;
	private String situacao; 
	private String prioridade;
	@OneToMany(mappedBy = "tarefa")
	private List<Link> links;
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario responsavel;
	private LocalDate cadastro;
	@ManyToOne(cascade = CascadeType.ALL)
	private Cliente cliente;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Pagamento> pagamento;

}
