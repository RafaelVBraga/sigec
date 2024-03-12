package com.rvbraga.sigec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank
	private String nome;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate dataNascimento;	
	
	private String profissao;
	
	@CPF(message="Cpf inválido")@NotBlank
	private String cpf;
	
	
	
	@NotBlank
	private String rg;
	
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)	
	private List<Telefone> telefones;
	
	
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate dataCadastro;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)		
	private Endereco endereco; 
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Documento> documentos; 
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Pagamento> pagamentos;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Processo> processos;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)	
	private Representante representante;	
	
	@ManyToMany
	@JoinTable(name ="cliente_herdeiros", joinColumns = @JoinColumn(name="cliente_id"), inverseJoinColumns = @JoinColumn(name="herdeiro_id"))
	private List<Cliente> herdeiros;
	
	public void update(Cliente cliente) {
		if(cliente.getNome()!=null) this.nome = cliente.getNome();
		if(cliente.getDataNascimento()!=null)this.dataNascimento = cliente.getDataNascimento();
		if(cliente.getDataCadastro()!=null)this.dataCadastro = cliente.getDataCadastro();
		if(cliente.getCpf()!=null) this.cpf = cliente.getCpf();
		if(cliente.getRg()!=null) this.rg = cliente.getRg();
		if(cliente.getEmail()!=null) this.email = cliente.getEmail();
		if(cliente.getProfissao()!=null)this.profissao = cliente.getProfissao();
		if(cliente.getEndereco().getLogradouro()!=null)this.endereco.setLogradouro(cliente.endereco.getLogradouro());
		if(cliente.getEndereco().getNumero()!=null)this.endereco.setNumero(cliente.endereco.getNumero());
		if(cliente.getEndereco().getComplemento()!=null)this.endereco.setComplemento(cliente.endereco.getComplemento());
		if(cliente.getEndereco().getBairro()!=null)this.endereco.setBairro(cliente.endereco.getBairro());
		if(cliente.getEndereco().getCep()!=null)this.endereco.setCep(cliente.endereco.getCep());	
	}
	

}
