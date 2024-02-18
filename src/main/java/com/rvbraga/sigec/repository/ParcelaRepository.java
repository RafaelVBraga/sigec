package com.rvbraga.sigec.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Pagamento;
import com.rvbraga.sigec.model.Parcela;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, UUID>{
	
		List<Parcela> findByPagamento(Pagamento pagamento);
	
	
}
