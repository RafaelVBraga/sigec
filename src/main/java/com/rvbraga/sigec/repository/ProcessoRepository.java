package com.rvbraga.sigec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvbraga.sigec.model.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, UUID>{

}
