package com.rvbraga.sigec.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvbraga.sigec.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID>{

}
