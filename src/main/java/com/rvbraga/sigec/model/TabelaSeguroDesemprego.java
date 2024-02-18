package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class TabelaSeguroDesemprego implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ano;
	private String fonte;
	@OneToMany(mappedBy = "tabela",fetch =FetchType.EAGER,cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Faixa> faixas;
	private Float piso;
	private Float teto;
	private Float salarioMinimo;
	

}
