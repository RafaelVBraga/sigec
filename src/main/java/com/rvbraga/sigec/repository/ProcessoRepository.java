package com.rvbraga.sigec.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	Optional<Processo> findByNumero(String numero);
	@Query("SELECT * FROM processo p WHERE (SELECT * FROM cliente c WHERE c.nome LIKE %:nome%) )")
	List<Processo> findByNomeCliente(@Param("nome") String cliente);
}
