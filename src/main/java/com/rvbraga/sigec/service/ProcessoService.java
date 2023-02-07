package com.rvbraga.sigec.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Processo;
import com.rvbraga.sigec.repository.ProcessoRepository;

@Service
public class ProcessoService {
	@Autowired
	private ProcessoRepository processoRepo;
	
	@Autowired
	private ClienteService clienteService;
	
	public Processo save(Processo processo) {
		return processoRepo.save(processo);
	}
	
	public void delete(Processo processo) {
		processoRepo.delete(processo);
	}
	public void delete(UUID id) {
		processoRepo.deleteById(id);
	}
	
	public List<Processo> findProcessosByCliente(UUID id){
		Cliente cliente = clienteService.findById(id);
		return processoRepo.findByCliente(cliente);
	}
	
	public List<Processo> findAll(){
		return processoRepo.findAll();
	}
	
	public Page<Processo> findAll(Pageable page){
		return processoRepo.findAll(page);
		
	}
	public List<Processo> findProcessosByNomeCliente(String cliente){
		return processoRepo.findByNomeCliente(cliente);
	}
	public List<Processo> findProcessosByCliente(Cliente cliente){
		return processoRepo.findByCliente(cliente);
	}
	public List<Processo> findByDataCadastro(LocalDate data){
		return processoRepo.findByDataCadastro(data);
	}
	public List<Processo> findByDataConclusao(LocalDate data){
		return processoRepo.findByDataConclusao(data);
	}
	public Optional<Processo> findById(UUID id){
		return processoRepo.findById(id);
	}
	public Optional<Processo> findByNumero(String numero){
		return processoRepo.findByNumero(numero);
	}
	
}
