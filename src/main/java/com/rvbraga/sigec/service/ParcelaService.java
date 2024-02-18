package com.rvbraga.sigec.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Pagamento;
import com.rvbraga.sigec.model.Parcela;
import com.rvbraga.sigec.repository.ParcelaRepository;

@Service
public class ParcelaService {
	@Autowired
	private ParcelaRepository parcelaRepository;
	
	public List<Parcela> findAll() {
		return parcelaRepository.findAll();
	}	
	public Parcela findById(UUID id) {
		return parcelaRepository.getReferenceById(id);
	}
	
	public List<Parcela> findByPagamento(Pagamento pagamento){
		return parcelaRepository.findByPagamento(pagamento);
	}
	public Parcela saveParcela(Parcela parcela) {
		
		return parcelaRepository.save(parcela);
	}
	public void deleteParcela(Parcela parcela) {
		parcelaRepository.delete(parcela);
	}
	
	
}
