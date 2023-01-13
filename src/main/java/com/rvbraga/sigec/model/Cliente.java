package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Cliente implements Serializable{
	
	private UUID id;
	private String nome;
	private Endereco endereco;
	private List<Documento> documentos;
	private List<Pagamento> pagamentos;
	private List<Processo> processos;
	

}
