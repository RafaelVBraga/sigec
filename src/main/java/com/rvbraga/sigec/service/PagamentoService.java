package com.rvbraga.sigec.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Pagamento;
import com.rvbraga.sigec.repository.PagamentoRepository;

@Service
public class PagamentoService {
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public List<Pagamento> findAll() {
		return pagamentoRepository.findAll();
	}	
	public Pagamento findById(UUID id) {
		return pagamentoRepository.getReferenceById(id);
	}
	
	public List<Pagamento> findByCliente(Cliente cliente) {
		return pagamentoRepository.findByCliente(cliente);
	}
	public Pagamento savePagamento(Pagamento pagamento) {
		
		return pagamentoRepository.save(pagamento);
	}
	public void deletePagamento(Pagamento pagamento) {
		pagamentoRepository.delete(pagamento);
	}
	
}
