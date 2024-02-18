package com.rvbraga.sigec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Documento;
import com.rvbraga.sigec.repository.DocumentoRepository;

@Service
public class DocumentoService {
	@Autowired
	private DocumentoRepository documentoRepository;
	
	public List<Documento> findAll() {
		return documentoRepository.findAll();
	}	
	public Documento findById(UUID id) {
		return documentoRepository.getReferenceById(id);
	}
//	public List<Documento> findByCliente(Cliente cliente) {
//		return documentoRepository.getByCliente(cliente.getId());
//	}
	public Documento save(Documento documento) {
		
		return documentoRepository.save(documento);
	}
	public List<Documento> saveAll(List<Documento> docs) {
		List<Documento> savedDocs = new ArrayList<Documento>();
		for(Documento doc : docs) {
			savedDocs.add(this.save(doc));
		}
		return savedDocs;
				
	}
	
	public Boolean deleteEndereco(Documento documento) {
		try {
			documentoRepository.deleteById(documento.getId());
			return true;
		}catch(Exception e) {
			return false;
		}
	}	
	
	public void deleteEndereco(UUID id) {
		documentoRepository.deleteById(id);
	}
}
