package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Parcela implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =GenerationType.UUID )
	private UUID id;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataPagamento;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataVencimento;
	private Boolean vencida;
	private Boolean paga;
	@Min(value=1)@NotNull
	private Float valor;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamento_id")
	private Pagamento pagamento;
	

}
