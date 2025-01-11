package com.rvbraga.sigec.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
	
	public Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);	
	public List<Cliente> findByNomeContainingIgnoreCase(String nome);
	public List<Cliente> findByCpfContaining(String cpf);
	public Cliente findByCpf(String cpf);
	public List<Cliente> findByRgContaining(String rg);
	public Cliente findByRg(String rg);
	List<Cliente> findAll(Sort sort);
	

}
