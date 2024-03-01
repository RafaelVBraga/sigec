package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
@Data
@Entity
public class Documento implements Serializable{ 
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	@Id
	@GeneratedValue(strategy = GenerationType.UUID) 
	private UUID id; 
	private String tipo;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataRecebimento;		
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataDevolucao;
	@NotNull@NotBlank
	private String local;
	private String respRecebimento;
	private String respDevolucao; 
	
	@ManyToOne
	private Cliente cliente;
	 
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		sb.append(" Tipo: "+this.tipo);
		try {
			sb.append("Dt Receb: "+this.dataRecebimento.format(formatter));
		}catch(Exception e) {
			sb.append("Dt Receb: NULL");
		}
		try {
			sb.append("Dt Devol: "+this.dataDevolucao.format(formatter));
		}catch(Exception e) {
			sb.append("Dt Devol: NULL");
		}
		sb.append(" Resp Recebimento: "+this.respRecebimento);
		sb.append(" Resp Devolucao: "+this.respDevolucao);
		sb.append(" Local: "+this.local);
		return sb.toString();
	}

}
