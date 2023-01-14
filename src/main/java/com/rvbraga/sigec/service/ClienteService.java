package com.rvbraga.sigec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Optional<Cliente> findByNome(String nome){
		return clienteRepository.findByNome(nome);
	}
	
	public Cliente saveCliente(Cliente cliente) {
		
		return clienteRepository.save(cliente);
	}

}
