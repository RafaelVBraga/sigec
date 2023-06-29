package com.rvbraga.sigec.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Endereco;
import com.rvbraga.sigec.repository.EnderecoRepository;

@Service
public class EnderecoService {
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}
	
	
	
	
	
	public Endereco saveEndereco(Endereco endereco) {
		
		return enderecoRepository.save(endereco);
	}
	
	public Boolean deleteEndereco(Endereco endereco) {
		try {
			enderecoRepository.delete(endereco);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	
	
	
	
	public void deleteEndereco(UUID id) {
		enderecoRepository.deleteById(id);
	}
}
