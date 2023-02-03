package com.rvbraga.sigec.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvbraga.sigec.model.Agendamento;
import com.rvbraga.sigec.model.Cliente;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID>{
	public Optional<Agendamento> findByDataAgendamento(LocalDate localDate);
	
	public List<Agendamento> findByMes(String mes);
	public List<Agendamento> findByAno(String ano);
	public List<Agendamento> findByMesAndAno(String mes, String ano);
	public List<Agendamento> findByDiaAndMesAndAno(String dia, String mes, String ano);
	public List<Agendamento> findByCliente(Cliente cliente);
}
