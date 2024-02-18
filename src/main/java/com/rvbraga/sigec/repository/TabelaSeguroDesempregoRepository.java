package com.rvbraga.sigec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.TabelaSeguroDesemprego;
@Repository
public interface TabelaSeguroDesempregoRepository extends JpaRepository<TabelaSeguroDesemprego, Long>{

}
