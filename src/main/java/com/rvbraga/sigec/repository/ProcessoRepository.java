package com.rvbraga.sigec.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, UUID>{
	
	List<Processo> findByCliente(Cliente cliente);
	
	Optional<Processo> findById(UUID id);
	Processo findByNumero(String numero);
	
	
	Page<Processo> findByClienteIn(List<Cliente> cliente, Pageable paging);
	
}
