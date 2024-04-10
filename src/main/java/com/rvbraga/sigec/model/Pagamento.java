package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Pagamento implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =GenerationType.UUID )
	private UUID id;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataCadastro;	
	@Min(value=1)@NotNull
	private Float valor;
	private String descricao;
	@Min(value = 1)@NotNull
	private Integer numeroParcelas; 
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@Valid
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Parcela> parcelas;

}
