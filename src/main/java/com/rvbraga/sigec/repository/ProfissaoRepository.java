package com.rvbraga.sigec.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Profissao;

@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, UUID>{

	
	Optional<Profissao> findByNomeIgnoreCase(String nome);
	
	
}
