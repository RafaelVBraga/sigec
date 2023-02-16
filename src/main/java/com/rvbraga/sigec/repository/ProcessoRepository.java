package com.rvbraga.sigec.repository;

import java.time.LocalDate;
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
	List<Processo> findByDataCadastro(LocalDate dataCadastro);
	List<Processo> findByDataConclusao(LocalDate dataConclusao);
	Optional<Processo> findById(UUID id);
	Processo findByNumero(String numero);
	@Query(value = "SELECT * FROM processo p WHERE p.cliente IN (SELECT * cliente c WHERE nome LIKE '%:nome%')",
			countQuery = "SELECT count(*) FROM processo p WHERE p.cliente IN (SELECT * cliente c WHERE nome LIKE '%:nome%')",
			nativeQuery = true)
	Page<Processo> findByNomeCliente(@Param("nome") String cliente, Pageable paging);
	
	Page<Processo> findByClienteIn(List<Cliente> cliente, Pageable paging);
	
}
