package com.rvbraga.sigec.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Documento;
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, UUID>{
	
	
	
	
	

}
