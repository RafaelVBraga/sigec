package com.rvbraga.sigec.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findByNome(String nome, Pageable paging){
		return clienteRepository.findByNomeContainingIgnoreCase(nome,paging);
	}
	
	public Page<Cliente> findAll(Pageable paging){
		return clienteRepository.findAll(paging);
	}
	
	public Cliente saveCliente(Cliente cliente) {
		
		return clienteRepository.save(cliente);
	}
	
	public Boolean deleteCliente(Cliente cliente) {
		try {
			clienteRepository.delete(cliente);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public Cliente findByCpf(String Cpf) {
		return clienteRepository.findByCpf(Cpf);
	}

	public Cliente findById(UUID id) {		
		return clienteRepository.findById(id).get();
	}
	
	 
	

}
