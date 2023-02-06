package com.rvbraga.sigec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rvbraga.sigec.dto.MensagemDto;
import com.rvbraga.sigec.dto.PesquisaProcessoDto;
import com.rvbraga.sigec.model.Processo;
import com.rvbraga.sigec.service.ProcessoService;

@Controller
@RequestMapping("/sigec")
public class ProcessoController {
	
	@Autowired
	private ProcessoService processoService;
	
	
	
	@GetMapping("/processos/cadastrar")
	public String cadastrar(Model model) { 
		return "";
	}
	
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
 

}
