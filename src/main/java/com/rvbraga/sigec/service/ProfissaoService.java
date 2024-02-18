package com.rvbraga.sigec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Profissao;
import com.rvbraga.sigec.repository.ProfissaoRepository;

@Service
public class ProfissaoService {
	
	@Autowired
	private ProfissaoRepository profissaoRepo;
	
	public List<Profissao> findAll(){
		return profissaoRepo.findAll();
	}
	
	public Boolean saveSafety(Profissao profissao) {
		if(profissaoRepo.findByNomeIgnoreCase(profissao.getNome()).isPresent())
			return false;
		else {
			profissaoRepo.save(profissao);
			return true;
		}
		
	}
}
