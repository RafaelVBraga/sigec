package com.rvbraga.sigec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID>{

}
