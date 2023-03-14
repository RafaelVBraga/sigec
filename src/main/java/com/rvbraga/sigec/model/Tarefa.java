package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class Tarefa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String descricao;
	private String tipoProcesso;
	private String StatusProcesso;
	private Usuario responsavel;
	private String situacao;
	private LocalDate cadastro;
	private String prioridade;
	@OneToMany(mappedBy = "tarefa")
	private List<Link> links; 

}
	