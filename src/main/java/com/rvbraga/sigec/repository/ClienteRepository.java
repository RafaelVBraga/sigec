package com.rvbraga.sigec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvbraga.sigec.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>{

}
