package com.rvbraga.sigec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rvbraga.sigec.dto.MensagemDto;
import com.rvbraga.sigec.dto.PesquisaDto;
import com.rvbraga.sigec.dto.PesquisaProcessoDto;
import com.rvbraga.sigec.model.Cliente;
import com.rvbraga.sigec.model.Processo;
import com.rvbraga.sigec.service.ProcessoService;

@Controller
@RequestMapping("/sigec")
public class ProcessoController {
	
	@Autowired
	private ProcessoService processoService;
	
	
	
	
	
	@GetMapping("/processos")
	public String processos(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size) { 
		
		PesquisaProcessoDto pesquisaProcesso = new PesquisaProcessoDto();
		pesquisaProcesso.setPaginas(size);
		model.addAttribute("pesquisaProcesso", pesquisaProcesso); 
		
 
		try {
			List<Processo> processos = new ArrayList<Processo>();
			Pageable paging = PageRequest.of(page - 1, size);

			Page<Processo> pageProcessos;
			pageProcessos = processoService.findAll(paging);

			processos = pageProcessos.getContent(); 		

			model.addAttribute("lista_processos", processos);
			model.addAttribute("currentPage", pageProcessos.getNumber()+1);
			model.addAttribute("totalItems", pageProcessos.getTotalElements());
			model.addAttribute("totalPages", pageProcessos.getTotalPages());
			model.addAttribute("pageSize", size);
			model.addAttribute("mensagem_tabela", processos.isEmpty() ? "Dados indisponíveis" : "");
 
		} catch (Exception e) {
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem(e.getMessage());
			mensagem.setStatus("Falha");
			model.addAttribute("feedback",mensagem);
			
			
			return "home.html";

		}
		return "processos.html";
	}
	@GetMapping("/processos/cadastrar")
	public String cadastrar(Model model) { 
		model.addAttribute("processo", new Processo());
		model.addAttribute("título_pagina", "Cadastro de Processos");  		
		return "processo_add_edit.html"; 
	}
	@PostMapping("/processos/pesquisa")
	public String pesquisaCliente(Model model, @Validated@ModelAttribute("pesquisaProcesso") PesquisaProcessoDto pesquisa,  Errors errors,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
		System.out.println("Iniciando pesquisa...");
		List<Processo> processos = new ArrayList<Processo>();
		if (errors.hasErrors()) { 
			
			model.addAttribute("lista_processos", processos);
			MensagemDto mensagem = new MensagemDto();
			mensagem.setMensagem("Corrija os campos assinalados!");
			mensagem.setStatus("AVISO");
			model.addAttribute("feedback",mensagem);
	        return "processo_add_edit.html"; 
	    }
		
		if (!pesquisa.getPesquisaCliente().isBlank())
			try {

				Pageable paging = PageRequest.of(page - 1, pesquisa.getPaginas());

				Page<Processo> pageProcesso;

				pageProcesso = processoService.findByNomeCliente(pesquisa.getPesquisaCliente(), paging);
				processos = pageProcesso.getContent();
				model.addAttribute("lista_processos", processos);
				model.addAttribute("currentPage", pageProcesso.getNumber() + 1);
				model.addAttribute("totalItems", pageProcesso.getTotalElements());
				model.addAttribute("totalPages", pageProcesso.getTotalPages());
				model.addAttribute("pageSize", size);
				model.addAttribute("mensagem_tabela", processos.isEmpty() ? "Dados indisponíveis para esta pesquisa..." : "");

			} catch (Exception e) {
				model.addAttribute("mensagem_tabela", e.getMessage());
				MensagemDto mensagem = new MensagemDto();
				mensagem.setMensagem(e.getMessage());
				mensagem.setStatus("FALHA");
				model.addAttribute("feedback",mensagem);
			}
		
		if (!pesquisa.getPesquisaNumero().isBlank()) {
			try {
				processos.add(processoService.findByNumero(pesquisa.getPesquisaNumero()));
				model.addAttribute("lista_processos", processos);
			}catch(Exception e) {
				
			}
		}
		if(pesquisa.getPesquisaNumero().isBlank()&&pesquisa.getPesquisaCliente().isBlank()) {
			Pageable paging = PageRequest.of(page - 1, pesquisa.getPaginas());

			Page<Processo> pageProcessos;
			pageProcessos = processoService.findAll(paging);

			processos = pageProcessos.getContent(); 		

			model.addAttribute("lista_processos", processos);
			model.addAttribute("currentPage", pageProcessos.getNumber()+1);
			model.addAttribute("totalItems", pageProcessos.getTotalElements());
			model.addAttribute("totalPages", pageProcessos.getTotalPages());
			model.addAttribute("pageSize", size);
			model.addAttribute("mensagem_tabela", processos.isEmpty() ? "Dados indisponíveis" : "");
			
		}
		System.out.println("Finalizando pesquisa...");
		return "processos.html";
	}
 

}
