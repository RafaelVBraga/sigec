package com.rvbraga.sigec.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
	
	public List<Cliente> findByNomeLike(String nome);
	public Cliente findByCpf(String cpf);

}
