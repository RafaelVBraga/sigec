package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Calculo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	private Float salarioBase;	
	@NotNull(message="Preenchimento necessário!")@Min(value =0)
	private Integer periculosidade;	
	@NotNull(message="Preenchimento necessário!")@Min(value =0)
	private Integer insalubridade;
	@NotNull(message="Preenchimento necessário!")
	private Integer horasMensais;
	@NotNull@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataAdmissao;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private LocalDate dataDemissao;
	private Float fgtsRescisorio;
	private Float valorHoraTrabalhada;
	private Float valorHoraExtra;
	private Float valorHoraNoturna;
	private Float valorHoraExtraNoturna;
	private Float valorFerias;
	private Float valorDecimoTerceiro;
	private Period tempoEmpresa;
	private String tempoEmpresaTexto;
	private Integer parcelasSeguro;
	private Float valorParcelaSeguro;
	private Float saldoSalario;
	private Float decimoTerceiroProporcional;
}
