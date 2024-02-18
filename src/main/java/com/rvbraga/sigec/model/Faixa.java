package com.rvbraga.sigec.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data@Entity
public class Faixa implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String descricao;
	private Float minimo;
	private Float maximo;
	private Float diferenca;
	private Float multiplicador;
	@ManyToOne(cascade=CascadeType.ALL)
	private TabelaSeguroDesemprego tabela;
	
}
