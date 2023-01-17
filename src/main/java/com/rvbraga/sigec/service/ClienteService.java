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
	
	public List<Cliente> findByNome(String nome){
		return clienteRepository.findByNomeLike(nome);
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

}
