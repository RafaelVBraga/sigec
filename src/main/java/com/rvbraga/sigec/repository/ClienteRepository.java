package com.rvbraga.sigec.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
	
	public Optional<Cliente> findByNome(String nome);
	public Cliente findByCpf(String cpf);

}
